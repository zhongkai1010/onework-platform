package com.onework.boot.task.collection.core.nmpa.store;

import com.onework.boot.task.collection.core.BaseStore;
import com.onework.boot.task.collection.dao.entity.NMPACollectionRecord;
import com.onework.boot.task.collection.dao.mapper.NMPACollectionRecordMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class NMPACollectionRecordStore extends BaseStore {

    private final NMPACollectionRecordMapper mapper;

    private final ConcurrentHashMap<String, NMPACollectionRecord> store = new ConcurrentHashMap<>();

    public NMPACollectionRecordStore(NMPACollectionRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void initData() {
        store.clear();
        for (NMPACollectionRecord record : mapper.selectList(null)) {
            store.put(record.getCompanyName(), record);
        }
    }

    public boolean isExist(String companyName) {
        return store.containsKey(companyName);
    }

    public NMPACollectionRecord get(String companyName) {
        return store.get(companyName);
    }

    public void addOrUpdate(NMPACollectionRecord record) {
        if (store.containsKey(record.getCompanyName())) {
            mapper.updateById(record);
        } else {
            mapper.insert(record);
            store.put(record.getCompanyName(), record);
        }
    }
}
