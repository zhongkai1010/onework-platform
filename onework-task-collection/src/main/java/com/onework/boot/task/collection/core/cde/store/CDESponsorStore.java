package com.onework.boot.task.collection.core.cde.store;

import com.onework.boot.task.collection.core.BaseStore;
import com.onework.boot.task.collection.dao.entity.CDESponsor;
import com.onework.boot.task.collection.dao.mapper.CDESponsorMapper;
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
