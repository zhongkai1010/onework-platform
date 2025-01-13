package com.onework.boot.scrape.site.cde.store;

import com.onework.boot.scrape.dal.dataobject.CDESponsor;
import com.onework.boot.scrape.site.BaseStore;
import com.onework.boot.scrape.dal.mysql.CDESponsorMapper;
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
