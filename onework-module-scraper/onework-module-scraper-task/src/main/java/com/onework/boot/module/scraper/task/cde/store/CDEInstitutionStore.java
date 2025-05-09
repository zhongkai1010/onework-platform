package com.onework.boot.module.scraper.task.cde.store;


import com.onework.boot.module.scraper.core.dal.dataobject.CDEInstitution;
import com.onework.boot.module.scraper.core.dal.mysql.CDEInstitutionMapper;
import com.onework.boot.module.scraper.task.BaseStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CDEInstitutionStore extends BaseStore {

    private final CDEInstitutionMapper mapper;

    private final ConcurrentHashMap<String, CDEInstitution> store = new ConcurrentHashMap<>();

    public CDEInstitutionStore(CDEInstitutionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void initData() {
        store.clear();
        List<CDEInstitution> institutions = mapper.selectList();
        for (CDEInstitution institution : institutions) {
            store.put(institution.getInstitutionName(), institution);
        }
    }

    public boolean isExist(String name) {
        return store.containsKey(name);
    }

    public CDEInstitution get(String name) {
        return store.get(name);
    }

    /**
     *  判断机构是否存在，不存在则新增
     * @param institution 机构信息
     */
    public void addOrUpdate(CDEInstitution institution) {
        if (store.containsKey(institution.getInstitutionName())) {
            mapper.updateById(institution);
        } else {
            mapper.insert(institution);
            store.put(institution.getInstitutionName(), institution);
        }
    }
}
