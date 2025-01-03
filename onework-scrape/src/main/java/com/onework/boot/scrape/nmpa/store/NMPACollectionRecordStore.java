package com.onework.boot.scrape.nmpa.store;

import com.onework.boot.scrape.BaseStore;
import com.onework.boot.scrape.data.entity.NmpaCollectionRecord;
import com.onework.boot.scrape.data.mapper.NmpaCollectionRecordMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class NMPACollectionRecordStore extends BaseStore {

    private final NmpaCollectionRecordMapper mapper;

    private final ConcurrentHashMap<String, NmpaCollectionRecord> store = new ConcurrentHashMap<>();

    public NMPACollectionRecordStore(NmpaCollectionRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void initData() {
        store.clear();
        for (NmpaCollectionRecord record : mapper.selectList(null)) {
            store.put(record.getCompanyName(), record);
        }
    }

    public boolean isExist(String companyName) {
        return store.containsKey(companyName);
    }

    public NmpaCollectionRecord get(String companyName) {
        return store.get(companyName);
    }

    public void addOrUpdate(NmpaCollectionRecord record) {
        if (store.containsKey(record.getCompanyName())) {
            mapper.updateById(record);
        } else {
            mapper.insert(record);
            store.put(record.getCompanyName(), record);
        }
    }
}
