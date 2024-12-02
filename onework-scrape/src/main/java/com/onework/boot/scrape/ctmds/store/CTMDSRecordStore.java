package com.onework.boot.scrape.ctmds.store;

import com.onework.boot.scrape.data.entity.CTMDSCollectionRecord;
import com.onework.boot.scrape.data.mapper.CTMDSCollectionRecordMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CTMDSRecordStore {

    private final CTMDSCollectionRecordMapper recordMapper;

    private final Map<String, CTMDSCollectionRecord> recordMap = new HashMap<>();

    public CTMDSRecordStore(CTMDSCollectionRecordMapper recordMapper) {
        this.recordMapper = recordMapper;
        initData();
    }

    private void initData() {
        List<CTMDSCollectionRecord> records = recordMapper.selectList(null);
        for (CTMDSCollectionRecord record : records) {
            recordMap.put(record.getInstitutionName(), record);
        }
    }

    public CTMDSCollectionRecord getRecord(String institutionName) {
        return recordMap.get(institutionName);
    }

    public boolean existRecord(String institutionName) {
        return recordMap.containsKey(institutionName);
    }

    public List<CTMDSCollectionRecord> getRecords() {
        return recordMap.values().stream().toList();
    }

    public void updateRecord(CTMDSCollectionRecord record) {
        recordMapper.updateById(record);
        recordMap.replace(record.getInstitutionName(), record);
    }

    public void insertRecord(CTMDSCollectionRecord record) {
        recordMapper.insert(record);
        recordMap.put(record.getInstitutionName(), record);
    }
}
