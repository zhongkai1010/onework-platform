package com.onework.boot.scrape.ctr.store;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.scrape.data.entity.CTRCollectionRecord;
import com.onework.boot.scrape.data.entity.CTRProject;
import com.onework.boot.scrape.data.mapper.CTRCollectionRecordMapper;
import com.onework.boot.scrape.data.mapper.CTRProjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CTRProjectRecordStore {

    private final CTRCollectionRecordMapper recordMapper;

    private final CTRProjectMapper projectMapper;

    private final Map<String, CTRCollectionRecord> store = new HashMap<>();

    public CTRProjectRecordStore(CTRCollectionRecordMapper recordMapper, CTRProjectMapper projectMapper) {
        this.recordMapper = recordMapper;
        this.projectMapper = projectMapper;
    }

    public void initData() {
        store.clear();
        for (CTRCollectionRecord record : recordMapper.selectList(null)) {
            store.put(record.getRegistrationNumber(), record);
        }
    }

    public boolean exist(String registrationNumber) {
        return !store.containsKey(registrationNumber);
    }

    public CTRCollectionRecord get(String registrationNumber) {
        return store.get(registrationNumber);
    }

    public List<CTRCollectionRecord> getNotDownloadProjects() {
        return store.values().stream().filter(record -> !record.getIsDownload()).toList();
    }

    public List<CTRCollectionRecord> getNotParseProjects() {
        return store.values().stream().filter(record -> !record.getIsParse()).toList();
    }

    public void markDownload(String registrationNumber, String path) {
        CTRCollectionRecord record = store.get(registrationNumber);
        record.setFilePath(path);
        record.setIsDownload(true);
        record.setDownloadDate(LocalDateTime.now());
        recordMapper.updateById(record);
        store.replace(registrationNumber, record);
    }

    public void markParse(String registrationNumber, CTRProject project) {
        CTRCollectionRecord record = store.get(registrationNumber);

        CTRProject oldProject = projectMapper.selectOne(Wrappers.<CTRProject>lambdaQuery().eq(CTRProject::getRegistrationNumber, record.getRegistrationNumber()));
        if (oldProject == null) {
            projectMapper.insert(project);
        } else {
            project.setUid(oldProject.getUid());
            projectMapper.updateById(project);
        }

        record.setIsParse(true);
        record.setParseDate(LocalDateTime.now());
        recordMapper.updateById(record);

        store.replace(registrationNumber, record);
    }

    public void add(CTRCollectionRecord record) {
        record.setIsParse(false);
        record.setIsDownload(false);
        recordMapper.insert(record);
        store.put(record.getRegistrationNumber(), record);
    }
}