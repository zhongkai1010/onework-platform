package com.onework.boot.scrape.site.cde.store;


import com.onework.boot.scrape.dal.dataobject.CDEResearcher;
import com.onework.boot.scrape.site.BaseStore;
import com.onework.boot.scrape.dal.mysql.CDEResearcherMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CDEResearcherStore extends BaseStore {

    private final CDEResearcherMapper researcherMapper;

    private final ConcurrentHashMap<String, CDEResearcher> store = new ConcurrentHashMap<>();


    public CDEResearcherStore(CDEResearcherMapper researcherMapper) {
        this.researcherMapper = researcherMapper;
    }


    public boolean isExist(String institutionName, String researcherName) {
        String key = getKey(institutionName, researcherName);
        return store.containsKey(key);
    }

    public CDEResearcher get(String institutionName, String researcherName) {
        String key = getKey(institutionName, researcherName);
        return store.get(key);
    }

    @Override
    public void initData() {
        store.clear();
        List<CDEResearcher> researchers = researcherMapper.selectList(null);
        for (CDEResearcher researcher : researchers) {
            String key = getKey(researcher.getInstitutionName(), researcher.getResearcherName());
            store.put(key, researcher);
        }
    }

    public void addOrUpdate(CDEResearcher researcher) {
        String key = getKey(researcher.getInstitutionName(), researcher.getResearcherName());
        if (store.containsKey(key)) {
            researcherMapper.updateById(researcher);
            store.replace(key, researcher);
        } else {
            researcherMapper.insert(researcher);
            store.put(key, researcher);
        }
    }

    public String getKey(String institutionName, String researcherName) {
        return String.format("%s_%s", institutionName, researcherName);
    }
}
