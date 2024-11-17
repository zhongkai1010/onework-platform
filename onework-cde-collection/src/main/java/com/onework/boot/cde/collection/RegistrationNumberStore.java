package com.onework.boot.cde.collection;

import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationNumberStore {

    private final CDECollectionRecordMapper recordMapper;

    private final List<String> store = new ArrayList<>();

    public RegistrationNumberStore(CDECollectionRecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    public void initData() {
        store.clear();
        for (CDECollectionRecord record : recordMapper.selectList(null)) {
            store.add(record.getRegistrationNumber());
        }
    }

    public boolean exist(String registrationNumber) {
        return !store.contains(registrationNumber);
    }

    public void add(String registrationNumber, String path) {
        CDECollectionRecord owCollectionCdeRecord = new CDECollectionRecord();
        owCollectionCdeRecord.setRegistrationNumber(registrationNumber);
        owCollectionCdeRecord.setCollectionDate(LocalDateTime.now());
        owCollectionCdeRecord.setParseHandle(false);
        owCollectionCdeRecord.setFilePath(path);
        recordMapper.insert(owCollectionCdeRecord);
        store.add(registrationNumber);
    }
}
