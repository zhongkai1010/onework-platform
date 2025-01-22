package com.onework.boot.task.collection.core.cde.store;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.task.collection.core.BaseStore;
import com.onework.boot.task.collection.dao.entity.CDECollectionRecord;
import com.onework.boot.task.collection.dao.entity.CDEProject;
import com.onework.boot.task.collection.dao.mapper.CDECollectionRecordMapper;
import com.onework.boot.task.collection.dao.mapper.CDEProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CDEProjectRecordStore extends BaseStore {

    private final CDECollectionRecordMapper recordMapper;

    private final ConcurrentHashMap<String, CDECollectionRecord> store = new ConcurrentHashMap<>();

    private final CDEProjectMapper projectMapper;

    public CDEProjectRecordStore(CDECollectionRecordMapper recordMapper, CDEProjectMapper projectMapper) {
        this.recordMapper = recordMapper;
        this.projectMapper = projectMapper;
    }

    public void initData() {
        store.clear();
        for (CDECollectionRecord record : recordMapper.selectList()) {
            store.put(record.getRegistrationNumber(), record);
        }
    }

    public boolean isExist(String registrationNumber) {
        return store.containsKey(registrationNumber);
    }

    public CDECollectionRecord get(String registrationNumber) {
        return store.get(registrationNumber);
    }

    public List<CDECollectionRecord> getNotDownloadProjects() {
        return store.values().stream().filter(record -> !record.getIsDownload()).toList();
    }

    public List<CDECollectionRecord> getNotParseProjects() {
        return store.values().stream().filter(record -> !record.getIsParse() && record.getIsDownload()).toList();
    }

    public void markDownload(String registrationNumber, String path) {
        CDECollectionRecord record = store.get(registrationNumber);
        record.setFilePath(path);
        record.setIsDownload(true);
        record.setDownloadDate(LocalDateTime.now());
        recordMapper.updateById(record);
        store.replace(registrationNumber, record);
    }

    public void markParse(String registrationNumber, CDEProject project) {
        CDECollectionRecord record = store.get(registrationNumber);
        record.setIsParse(true);
        record.setParseDate(LocalDateTime.now());
        recordMapper.updateById(record);

        CDEProject oldProject = projectMapper.selectOne(CDEProject::getRegistrationNumber, record.getRegistrationNumber());
        if (oldProject == null) {
            projectMapper.insert(project);
        } else {
            project.setUid(oldProject.getUid());
            projectMapper.updateById(project);
        }
        store.replace(registrationNumber, record);
    }

    public void addOrUpdate(CDECollectionRecord record) {
        if (store.containsKey(record.getRegistrationNumber())) {
            recordMapper.updateById(record);
        } else {
            recordMapper.insert(record);
            store.put(record.getRegistrationNumber(), record);
        }
    }
}
