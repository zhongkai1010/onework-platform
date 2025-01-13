package com.onework.boot.scrape.site.cde;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.scrape.dal.dataobject.CDEInstitution;
import com.onework.boot.scrape.dal.dataobject.CDEProject;
import com.onework.boot.scrape.dal.dataobject.CDEResearcher;
import com.onework.boot.scrape.dal.dataobject.CDESponsor;
import com.onework.boot.scrape.site.ScrapeHelper;
import com.onework.boot.scrape.site.TaskServer;
import com.onework.boot.scrape.site.TaskServerType;
import com.onework.boot.scrape.site.cde.config.CDEAnalysisConfiguration;
import com.onework.boot.scrape.site.cde.dtos.*;
import com.onework.boot.scrape.site.cde.store.CDEInstitutionStore;
import com.onework.boot.scrape.site.cde.store.CDEResearcherStore;
import com.onework.boot.scrape.site.cde.store.CDESponsorStore;
import com.onework.boot.scrape.dal.mysql.CDEProjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class CDEAnalysisTaskServer extends TaskServer {

    private final CDEProjectMapper projectMapper;

    private final CDESponsorStore cdeSponsorStore;

    private final CDEInstitutionStore cdeInstitutionStore;

    private final CDEResearcherStore cdeResearcherStore;

    private final CDEAnalysisConfiguration configuration;

    public CDEAnalysisTaskServer(CDEProjectMapper projectMapper, CDESponsorStore cdeSponsorStore, CDEInstitutionStore cdeInstitutionStore, CDEResearcherStore cdeResearcherStore, CDEAnalysisConfiguration configuration) {
        this.projectMapper = projectMapper;
        this.cdeSponsorStore = cdeSponsorStore;
        this.cdeInstitutionStore = cdeInstitutionStore;
        this.cdeResearcherStore = cdeResearcherStore;
        this.configuration = configuration;
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CDE_ANALYSIS;
    }

    @Override
    protected void beforeStart() {
        cdeSponsorStore.initData();
        cdeInstitutionStore.initData();
        cdeResearcherStore.initData();
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();
        List<CDEProject> projects = projectMapper.selectList(Wrappers.<CDEProject>lambdaQuery().eq(CDEProject::getIsAnalysis, false));
        log.info("[{}],共有{}条", desc, projects.size());

        int threadCount = configuration.getThreadCount();
        ConcurrentHashMap<String, ProjectSponsor> projectSponsorHashMap = new ConcurrentHashMap<>();
        ScrapeHelper.listWorkerExecute(projects, threadCount, (start, end, items) -> processSponsor(projectSponsorHashMap, items));
        log.info("申办方已解析完成,共计:{}家", projectSponsorHashMap.values().size());

        List<ProjectSponsor> projectSponsors = projectSponsorHashMap.values().stream().toList();
        saveSponsor(projectSponsors);
        log.info("申办方数据记录完成,共计:{}家", projectSponsors.size());

        ConcurrentHashMap<String, ProjectInstitution> projectInstitutionHashMap = new ConcurrentHashMap<>();
        ScrapeHelper.listWorkerExecute(projects, threadCount, (start, end, items) -> processInstitutionByParticipate(projectInstitutionHashMap, items));
        ScrapeHelper.listWorkerExecute(projects, threadCount, (start, end, items) -> processInstitutionByInvestigator(projectInstitutionHashMap, items));

        log.info("临床机构已解析完成,共计:{}家", projectInstitutionHashMap.values().size());

        List<ProjectInstitution> projectInstitutions = projectInstitutionHashMap.values().stream().toList();
        saveInstitution(projectInstitutions);
        log.info("临床机构数据记录完成,共计:{}家", projectInstitutions.size());

        ConcurrentHashMap<String, ProjectResearcher> projectResearcherHashMap = new ConcurrentHashMap<>();
        ScrapeHelper.listWorkerExecute(projects, threadCount, (start, end, items) -> processResearcher(projectResearcherHashMap, items));
        log.info("机构研究者,共计:{}位", projectResearcherHashMap.values().size());

        List<ProjectResearcher> projectResearchers = projectResearcherHashMap.values().stream().toList();
        saveResearcher(projectResearchers);
        log.info("机构研究者数据记录完成,共计:{}位", projectResearchers.size());

    }

    private void saveResearcher(List<ProjectResearcher> data) {
        ScrapeHelper.listWorkerExecute(data, configuration.getThreadCount(), (start, end, items) -> {
            for (ProjectResearcher projectResearcher : items) {
                CDEResearcher cdeResearcher;
                if (cdeResearcherStore.isExist(projectResearcher.getInstitutionName(), projectResearcher.getResearcherName())) {
                    cdeResearcher = cdeResearcherStore.get(projectResearcher.getInstitutionName(), projectResearcher.getResearcherName());
                } else {
                    cdeResearcher = new CDEResearcher();
                }
                cdeResearcher.setInstitutionName(projectResearcher.getInstitutionName());
                cdeResearcher.setResearcherName(projectResearcher.getResearcherName());
                cdeResearcher.setDegree(projectResearcher.getDegree());
                cdeResearcher.setTitle(projectResearcher.getTitle());
                cdeResearcher.setPhone(projectResearcher.getPhone());
                cdeResearcher.setEmail(projectResearcher.getEmail());
                cdeResearcher.setProjects(JSON.toJSONString(projectResearcher.getProjects()));
                cdeResearcherStore.addOrUpdate(cdeResearcher);
            }
        });
    }

    private void saveInstitution(List<ProjectInstitution> data) {
        ScrapeHelper.listWorkerExecute(data, configuration.getThreadCount(), (start, end, items) -> {
            for (ProjectInstitution projectInstitution : items) {
                CDEInstitution cdeInstitution;
                if (cdeInstitutionStore.isExist(projectInstitution.getInstitutionName())) {
                    cdeInstitution = cdeInstitutionStore.get(projectInstitution.getInstitutionName());
                } else {
                    cdeInstitution = new CDEInstitution();
                }
                cdeInstitution.setInstitutionName(projectInstitution.getInstitutionName());
                cdeInstitution.setCountry(projectInstitution.getCountry());
                cdeInstitution.setProvince(projectInstitution.getProvince());
                cdeInstitution.setCity(projectInstitution.getCity());
                cdeInstitution.setAddress(projectInstitution.getAddress());
                cdeInstitution.setPostalCode(projectInstitution.getPostalCode());
                cdeInstitution.setProjects(JSON.toJSONString(projectInstitution.getProjects()));
                cdeInstitutionStore.addOrUpdate(cdeInstitution);
            }
        });
    }

    private void saveSponsor(List<ProjectSponsor> data) {
        ScrapeHelper.listWorkerExecute(data, configuration.getThreadCount(), (start, end, items) -> {
            for (ProjectSponsor projectSponsor : items) {
                CDESponsor cdeSponsor;
                if (cdeSponsorStore.isExist(projectSponsor.getSponsorName())) {
                    cdeSponsor = cdeSponsorStore.get(projectSponsor.getSponsorName());
                } else {
                    cdeSponsor = new CDESponsor();
                }
                cdeSponsor.setSponsorName(projectSponsor.getSponsorName());
                cdeSponsor.setContactPersonName(projectSponsor.getContactPersonName());
                cdeSponsor.setContactLandline(projectSponsor.getContactLandline());
                cdeSponsor.setContactMobile(projectSponsor.getContactMobile());
                cdeSponsor.setContactEmail(projectSponsor.getContactEmail());
                cdeSponsor.setContactAddress(projectSponsor.getContactAddress());
                cdeSponsor.setContactPostcode(projectSponsor.getContactPostcode());
                cdeSponsor.setProjects(JSON.toJSONString(projectSponsor.getProjects()));
                cdeSponsorStore.addOrUpdate(cdeSponsor);
            }
        });
    }

    private void processResearcher(@NotNull ConcurrentHashMap<String, ProjectResearcher> map, @NotNull List<CDEProject> projects) {
        for (CDEProject project : projects) {
            List<CDEPrincipalInvestigator> cdePrincipalInvestigators = JSON.parseArray(project.getPrincipalInvestigatorInfo(), CDEPrincipalInvestigator.class);
            for (CDEPrincipalInvestigator cdePrincipalInvestigator : cdePrincipalInvestigators) {
                addOrUpdateResearcher(map, project, cdePrincipalInvestigator);
            }
        }
        for (CDEProject project : projects) {
            List<CDEClinicalInstitution> clinicalInstitutions = JSON.parseArray(project.getParticipatingInstitutionsInfo(), CDEClinicalInstitution.class);
            for (CDEClinicalInstitution cdePrincipalInvestigator : clinicalInstitutions) {
                addOrUpdateResearcher(map, project, cdePrincipalInvestigator);
            }
        }
    }

    private void addOrUpdateResearcher(ConcurrentHashMap<String, ProjectResearcher> map, CDEProject project, CDEClinicalInstitution cdeClinicalInstitution) {
        String key = String.format("%s-%s", cdeClinicalInstitution.getOrganizationName(), cdeClinicalInstitution.getPrincipal());
        if (map.containsKey(key)) {
            ProjectResearcher projectResearcher = map.get(key);
            projectResearcher.setInstitutionName(cdeClinicalInstitution.getOrganizationName());
            projectResearcher.setResearcherName(cdeClinicalInstitution.getPrincipal());
            List<String> projects = projectResearcher.getProjects();
            if (!projects.contains(project.getRegistrationNumber())) {
                projects.add(project.getRegistrationNumber());
            }
            projectResearcher.setProjects(projects);
            map.replace(key, projectResearcher);
        } else {
            ProjectResearcher projectResearcher = new ProjectResearcher();
            projectResearcher.setInstitutionName(cdeClinicalInstitution.getOrganizationName());
            projectResearcher.setResearcherName(cdeClinicalInstitution.getPrincipal());
            List<String> projects = new ArrayList<>();
            projects.add(project.getRegistrationNumber());
            projectResearcher.setProjects(projects);
            map.put(key, projectResearcher);
        }
    }

    private void addOrUpdateResearcher(ConcurrentHashMap<String, ProjectResearcher> map, CDEProject project, CDEPrincipalInvestigator cdePrincipalInvestigator) {
        String key = String.format("%s-%s", cdePrincipalInvestigator.getOrganizationName(), cdePrincipalInvestigator.getName());
        if (map.containsKey(key)) {
            ProjectResearcher projectResearcher = map.get(key);
            projectResearcher.setPhone(cdePrincipalInvestigator.getOrganizationName());
            projectResearcher.setEmail(cdePrincipalInvestigator.getEmail());
            projectResearcher.setDegree(cdePrincipalInvestigator.getDegree());
            projectResearcher.setTitle(cdePrincipalInvestigator.getTitle());
            List<String> projects = projectResearcher.getProjects();
            if (!projects.contains(project.getRegistrationNumber())) {
                projects.add(project.getRegistrationNumber());
            }
            projectResearcher.setProjects(projects);
            map.replace(key, projectResearcher);
        } else {
            ProjectResearcher projectResearcher = new ProjectResearcher();
            projectResearcher.setInstitutionName(cdePrincipalInvestigator.getOrganizationName());
            projectResearcher.setResearcherName(cdePrincipalInvestigator.getName());
            projectResearcher.setEmail(cdePrincipalInvestigator.getEmail());
            projectResearcher.setDegree(cdePrincipalInvestigator.getDegree());
            projectResearcher.setTitle(cdePrincipalInvestigator.getTitle());
            List<String> projects = new ArrayList<>();
            projects.add(project.getRegistrationNumber());
            projectResearcher.setProjects(projects);
            map.put(key, projectResearcher);
        }
    }

    private void processInstitutionByParticipate(@NotNull ConcurrentHashMap<String, ProjectInstitution> map, @NotNull List<CDEProject> projects) {
        for (CDEProject project : projects) {
            List<CDEClinicalInstitution> CDEClinicalInstitutions = JSON.parseArray(project.getParticipatingInstitutionsInfo(), CDEClinicalInstitution.class);
            for (CDEClinicalInstitution cdeClinicalInstitution : CDEClinicalInstitutions) {
                addOrUpdateInstitution(map, project, cdeClinicalInstitution);
            }
        }
    }

    private void processInstitutionByInvestigator(@NotNull ConcurrentHashMap<String, ProjectInstitution> map, @NotNull List<CDEProject> projects) {
        for (CDEProject project : projects) {
            List<CDEPrincipalInvestigator> CDEPrincipalInvestigators = JSON.parseArray(project.getPrincipalInvestigatorInfo(), CDEPrincipalInvestigator.class);
            for (CDEPrincipalInvestigator cdePrincipalInvestigator : CDEPrincipalInvestigators) {
                addOrUpdateInstitution(map, project, cdePrincipalInvestigator);
            }
        }
    }

    private void addOrUpdateInstitution(@NotNull ConcurrentHashMap<String, ProjectInstitution> map, @NotNull CDEProject project, @NotNull CDEPrincipalInvestigator cdePrincipalInvestigator) {
        String institutionName = cdePrincipalInvestigator.getOrganizationName();
        if (map.containsKey(institutionName)) {
            ProjectInstitution projectInstitution = map.get(institutionName);
            String postalAddress = cdePrincipalInvestigator.getPostalAddress();
            if (postalAddress.contains("-")) {
                String[] addresses = postalAddress.split("-");
                projectInstitution.setProvince(addresses[0]);
                projectInstitution.setCity(addresses[1]);
                projectInstitution.setAddress(addresses[2]);
            } else {
                projectInstitution.setAddress(cdePrincipalInvestigator.getPostalAddress());
            }
            projectInstitution.setPostalCode(cdePrincipalInvestigator.getPostalCode());
            List<String> projects = projectInstitution.getProjects();
            if (!projects.contains(project.getRegistrationNumber())) {
                projects.add(project.getRegistrationNumber());
            }
            projectInstitution.setProjects(projects);
            map.replace(institutionName, projectInstitution);
        } else {
            ProjectInstitution projectInstitution = new ProjectInstitution();
            projectInstitution.setInstitutionName(institutionName);
            String postalAddress = cdePrincipalInvestigator.getPostalAddress();
            if (postalAddress.contains("-")) {
                String[] addresses = postalAddress.split("-");
                projectInstitution.setProvince(addresses[0]);
                projectInstitution.setCity(addresses[1]);
                projectInstitution.setAddress(addresses[2]);
            } else {
                projectInstitution.setAddress(cdePrincipalInvestigator.getPostalAddress());
            }
            projectInstitution.setPostalCode(cdePrincipalInvestigator.getPostalCode());
            List<String> projects = new ArrayList<>();
            projects.add(project.getRegistrationNumber());
            projectInstitution.setProjects(projects);
            map.put(institutionName, projectInstitution);
        }
    }

    private void addOrUpdateInstitution(@NotNull ConcurrentHashMap<String, ProjectInstitution> map, @NotNull CDEProject project, CDEClinicalInstitution cdeClinicalInstitution) {
        String institutionName = cdeClinicalInstitution.getOrganizationName();
        if (map.containsKey(institutionName)) {
            ProjectInstitution institution = map.get(institutionName);
            institution.setCountry(cdeClinicalInstitution.getRegion());
            institution.setProvince(cdeClinicalInstitution.getProvince());
            institution.setCity(cdeClinicalInstitution.getCity());
            List<String> projects = institution.getProjects();
            if (!projects.contains(project.getRegistrationNumber())) {
                projects.add(project.getRegistrationNumber());
            }
            institution.setProjects(projects);
            map.replace(institutionName, institution);
        } else {
            ProjectInstitution institution = new ProjectInstitution();
            institution.setInstitutionName(institutionName);
            institution.setCountry(cdeClinicalInstitution.getRegion());
            institution.setProvince(cdeClinicalInstitution.getProvince());
            institution.setCity(cdeClinicalInstitution.getCity());
            List<String> sponsorProjects = new ArrayList<>();
            sponsorProjects.add(project.getRegistrationNumber());
            institution.setProjects(sponsorProjects);
            map.put(institutionName, institution);
        }
    }

    private void processSponsor(@NotNull ConcurrentHashMap<String, ProjectSponsor> map, @NotNull List<CDEProject> projects) {
        for (CDEProject project : projects) {
            List<String> cdeProjectSponsor = JSON.parseArray(project.getApplicantName(), String.class);
            for (String sponsorName : cdeProjectSponsor) {
                if (sponsorName.contains("-") || sponsorName.contains("/") || sponsorName.contains("?")) {
                    continue;
                }
                if (sponsorName.contains("；")) {
                    List<String> sponsors = Arrays.stream(sponsorName.split("；")).toList();
                    for (String sponsor : sponsors) {
                        addOrUpdateSponsor(map, project, sponsor);
                    }
                } else {
                    addOrUpdateSponsor(map, project, sponsorName);
                }
            }
        }
    }

    private void addOrUpdateSponsor(@NotNull ConcurrentHashMap<String, ProjectSponsor> map, @NotNull CDEProject project, @NotNull String sponsorName) {
        ProjectSponsor projectSponsor;
        if (map.containsKey(sponsorName)) {
            projectSponsor = map.get(sponsorName);
            projectSponsor.setContactPersonName(project.getContactName());
            projectSponsor.setContactLandline(project.getContactLandline());
            projectSponsor.setContactEmail(project.getContactEmail());
            projectSponsor.setContactAddress(project.getContactAddress());
            projectSponsor.setContactMobile(project.getContactMobile());
            projectSponsor.setContactPostcode(project.getContactZipCode());
            List<String> projects = projectSponsor.getProjects();
            if (!projects.contains(project.getRegistrationNumber())) {
                projects.add(project.getRegistrationNumber());
            }
            projectSponsor.setProjects(projects);
            map.replace(sponsorName, projectSponsor);
        } else {
            projectSponsor = new ProjectSponsor();
            projectSponsor.setSponsorName(sponsorName);
            projectSponsor.setContactPersonName(project.getContactName());
            projectSponsor.setContactLandline(project.getContactLandline());
            projectSponsor.setContactEmail(project.getContactEmail());
            projectSponsor.setContactAddress(project.getContactAddress());
            projectSponsor.setContactMobile(project.getContactMobile());
            projectSponsor.setContactPostcode(project.getContactZipCode());
            List<String> projects = new ArrayList<>();
            projects.add(project.getRegistrationNumber());
            projectSponsor.setProjects(projects);
            map.put(sponsorName, projectSponsor);
        }
    }
}
