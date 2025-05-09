package com.onework.boot.module.scraper.task.cde.store;

import com.onework.boot.module.scraper.core.dal.dataobject.CDESponsor;
import com.onework.boot.module.scraper.core.dal.mysql.CDESponsorMapper;
import com.onework.boot.module.scraper.task.BaseStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CDESponsorStore extends BaseStore {

    private final CDESponsorMapper sponsorMapper;

    private final ConcurrentHashMap<String, CDESponsor> store = new ConcurrentHashMap<>();

    public CDESponsorStore(CDESponsorMapper sponsorMapper) {
        this.sponsorMapper = sponsorMapper;
    }

    @Override
    public void initData() {
        store.clear();
        List<CDESponsor> sponsors = sponsorMapper.selectList(null);
        for (CDESponsor sponsor : sponsors) {
            store.put(sponsor.getSponsorName(), sponsor);
        }
    }

    public boolean isExist(String name) {
        return store.containsKey(name);
    }

    public CDESponsor get(String name) {
        return store.get(name);
    }

    public void addOrUpdate(CDESponsor sponsor) {
        if (store.containsKey(sponsor.getSponsorName())) {
            sponsorMapper.updateById(sponsor);
        } else {
            sponsorMapper.insert(sponsor);
            store.put(sponsor.getSponsorName(), sponsor);
        }
    }
}
