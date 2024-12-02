package com.onework.boot.scrape.cde.store;


import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.data.entity.CDEResearcher;
import com.onework.boot.scrape.data.mapper.CDEResearcherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CDEResearcherStore {

    private final CDEResearcherMapper researcherMapper;

    private final Map<String, CDEResearcher> researcherStore = new HashMap<>();

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CDEResearcherStore(CDEResearcherMapper researcherMapper) {
        this.researcherMapper = researcherMapper;
        initDate();
    }

    private void initDate() {
        List<CDEResearcher> researchers = researcherMapper.selectList(null);
        for (CDEResearcher researcher : researchers) {
            String key = String.format("%s-%s", researcher.getInstitutionName(), researcher.getResearcherName());
            researcherStore.put(key, researcher);
        }
    }

    public void checkAndInsertIfNotExist(CDEResearcher researcher) {
        try {
            String key = String.format("%s-%s", researcher.getInstitutionName(), researcher.getResearcherName());
            if (researcherStore.containsKey(key)) {
                CDEResearcher cdeResearcher = researcherStore.get(key);
                cdeResearcher.setDegree(researcher.getDegree());
                cdeResearcher.setTitle(researcher.getTitle());
                cdeResearcher.setPhone(researcher.getPhone());
                cdeResearcher.setEmail(researcher.getEmail());
                researcherMapper.updateById(cdeResearcher);
                researcherStore.replace(key, cdeResearcher);
                LOG.info("更新研究者：{}，机构：{}", researcher.getResearcherName(), researcher.getInstitutionName());
            } else {
                researcherMapper.insert(researcher);
                researcherStore.put(key, researcher);
                LOG.info("新增研究者：{}，机构：{}", researcher.getResearcherName(), researcher.getInstitutionName());
            }
        } catch (Exception exception) {
            LOG.error("机构：{}，研究者：{}，出现异常，错误消息：{}", researcher.getInstitutionName(), researcher.getResearcherName(), exception.getMessage());
        }
    }
}
