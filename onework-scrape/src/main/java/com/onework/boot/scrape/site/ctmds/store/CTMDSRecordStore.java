package com.onework.boot.scrape.site.ctmds.store;

import com.onework.boot.scrape.site.BaseStore;
import com.onework.boot.scrape.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.scrape.dal.mysql.CTMDSCollectionRecordMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CTMDSRecordStore extends BaseStore {

    private final CTMDSCollectionRecordMapper mapper;

    private final ConcurrentHashMap<String, CTMDSCollectionRecord> store = new ConcurrentHashMap<>();

    public CTMDSRecordStore(CTMDSCollectionRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void initData() {
        store.clear();
        List<CTMDSCollectionRecord> records = mapper.selectList(null);
        for (CTMDSCollectionRecord record : records) {
            store.put(record.getRecordNumber(), record);
        }
    }

    public CTMDSCollectionRecord get(String recordNumber) {
        return store.get(recordNumber);
    }

    public boolean isExist(String recordNumber) {
        return store.containsKey(recordNumber);
    }

    public List<CTMDSCollectionRecord> getDrugRecords() {
        return store.values().stream().filter(ctmdsCollectionRecord -> ctmdsCollectionRecord.getRecordNumber().contains("药临床机构备字")).toList();
    }

    public List<CTMDSCollectionRecord> getInstrumentRecords() {
        return store.values().stream().filter(ctmdsCollectionRecord -> ctmdsCollectionRecord.getRecordNumber().contains("械临机构备")).toList();
    }

    public void addOrUpdate(CTMDSCollectionRecord record) {
        String key = record.getRecordNumber();
        if (store.containsKey(key)) {
            mapper.updateById(record);
            store.replace(record.getRecordNumber(), record);
        } else {
            mapper.insert(record);
            store.put(record.getRecordNumber(), record);
        }
    }
}
