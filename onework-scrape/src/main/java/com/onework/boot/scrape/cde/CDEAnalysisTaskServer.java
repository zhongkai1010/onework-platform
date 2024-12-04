package com.onework.boot.scrape.cde;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.cde.dtos.ParticipatingOrganizationsInformation;
import com.onework.boot.scrape.cde.dtos.PrincipalInvestigatorInformation;
import com.onework.boot.scrape.cde.store.CDEInstitutionStore;
import com.onework.boot.scrape.cde.store.CDEResearcherStore;
import com.onework.boot.scrape.cde.store.CDESponsorStore;
import com.onework.boot.scrape.data.entity.CDEInstitution;
import com.onework.boot.scrape.data.entity.CDEProject;
import com.onework.boot.scrape.data.entity.CDEResearcher;
import com.onework.boot.scrape.data.entity.CDESponsor;
import com.onework.boot.scrape.data.mapper.CDEProjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CDEAnalysisTaskServer implements ITaskServer {

    private final CDEProjectMapper projectMapper;

    private final CDESponsorStore cdeSponsorStore;

    private final CDEInstitutionStore cdeInstitutionStore;

    private final CDEResearcherStore cdeResearcherStore;

    public CDEAnalysisTaskServer(CDEProjectMapper projectMapper, CDESponsorStore cdeSponsorStore, CDEInstitutionStore cdeInstitutionStore, CDEResearcherStore cdeResearcherStore) {
        this.projectMapper = projectMapper;
        this.cdeSponsorStore = cdeSponsorStore;
        this.cdeInstitutionStore = cdeInstitutionStore;
        this.cdeResearcherStore = cdeResearcherStore;

        this.cdeSponsorStore.initDate();
        this.cdeInstitutionStore.initData();
        this.cdeResearcherStore.initDate();

    }

    @Override
    public void run() {
        List<CDEProject> projects = projectMapper.selectList(Wrappers.<CDEProject>lambdaQuery().eq(CDEProject::getIsAnalysis, false));
        for (CDEProject project : projects) {
            List<String> applicantNames = JSON.parseArray(project.getApplicantName(), String.class);
            // 申办方
            for (String applicantName : applicantNames) {
                CDESponsor sponsor = new CDESponsor();
                sponsor.setSponsorName(applicantName);
                sponsor.setContactPersonName(project.getContactName());
                sponsor.setContactLandline(project.getContactLandline());
                sponsor.setContactMobile(project.getContactMobile());
                sponsor.setContactEmail(project.getContactEmail());
                sponsor.setContactAddress(project.getContactAddress());
                sponsor.setContactPostcode(project.getContactZipCode());
                cdeSponsorStore.checkAndInsertIfNotExist(sponsor);
            }
            // 机构与研究者
            List<ParticipatingOrganizationsInformation> informations = JSON.parseArray(project.getParticipatingInstitutionsInfo(), ParticipatingOrganizationsInformation.class);
            for (ParticipatingOrganizationsInformation information : informations) {
                CDEInstitution cdeInstitution = new CDEInstitution();
                cdeInstitution.setInstitutionName(information.getOrganizationName());
                cdeInstitution.setCountry(information.getRegion());
                cdeInstitution.setProvince(information.getProvince());
                cdeInstitution.setCity(information.getCity());
                cdeInstitutionStore.checkAndInsertIfNotExist(cdeInstitution);

                CDEResearcher researcher = new CDEResearcher();
                researcher.setInstitutionName(information.getOrganizationName());
                researcher.setResearcherName(information.getPrincipal());
                cdeResearcherStore.checkAndInsertIfNotExist(researcher);
            }

            // 主要研究者
            List<PrincipalInvestigatorInformation> principalInvestigatorInformations = JSON.parseArray(project.getPrincipalInvestigatorInfo(), PrincipalInvestigatorInformation.class);
            for (PrincipalInvestigatorInformation information : principalInvestigatorInformations) {
                CDEResearcher researcher = new CDEResearcher();
                researcher.setInstitutionName(information.getOrganizationName());
                researcher.setResearcherName(information.getName());
                researcher.setDegree(information.getDegree());
                researcher.setPhone(information.getPhone());
                researcher.setEmail(information.getEmail());
                cdeResearcherStore.checkAndInsertIfNotExist(researcher);
            }
        }
    }
}
