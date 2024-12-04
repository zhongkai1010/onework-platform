package com.onework.boot.scrape.ctmds.store;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.data.entity.CTMDSCollectionRecord;
import com.onework.boot.scrape.data.mapper.CTMDSCollectionRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(0)
@Component
public class CTMDSRecordStore {

    private final CTMDSCollectionRecordMapper recordMapper;


    private final Map<String, CTMDSCollectionRecord> recordMap = new HashMap<>();

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CTMDSRecordStore(CTMDSCollectionRecordMapper recordMapper) {
        this.recordMapper = recordMapper;
    }

    public void initData() {
        LOG.info("初始化CTMDS系统数据");
        List<CTMDSCollectionRecord> records = recordMapper.selectList(null);
        for (CTMDSCollectionRecord record : records) {
            recordMap.put(record.getRecordNumber(), record);
        }
        LOG.info("完成初始化CTMDS系统数据");
    }

    public CTMDSCollectionRecord getRecord(String recordNumber) {
        return recordMap.get(recordNumber);
    }

    public boolean existRecord(String recordNumber) {
        return recordMap.containsKey(recordNumber);
    }

    public List<CTMDSCollectionRecord> getRecords() {
        return recordMap.values().stream().toList();
    }

    public List<CTMDSCollectionRecord> getDrugRecords() {
        return recordMap.values().stream().filter(ctmdsCollectionRecord -> ctmdsCollectionRecord.getRecordNumber().contains("药临床机构备字")).toList();
    }

    public List<CTMDSCollectionRecord> getInstrumentRecords() {
        return recordMap.values().stream().filter(ctmdsCollectionRecord -> ctmdsCollectionRecord.getRecordNumber().contains("械临机构备")).toList();
    }

    public void updateRecord(CTMDSCollectionRecord record) {
        recordMapper.updateById(record);
        recordMap.replace(record.getRecordNumber(), record);
    }

    public void insertRecord(CTMDSCollectionRecord record) {
        recordMapper.insert(record);
        recordMap.put(record.getRecordNumber(), record);
    }
}
