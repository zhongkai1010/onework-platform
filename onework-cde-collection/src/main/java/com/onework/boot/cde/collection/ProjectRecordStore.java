package com.onework.boot.cde.collection;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.entity.CDEProject;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import com.onework.boot.data.mapper.CDEProjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProjectRecordStore {

    private final CDECollectionRecordMapper recordMapper;

    private final Map<String, CDECollectionRecord> store = new HashMap<>();

    private final CDEProjectMapper projectMapper;

    public ProjectRecordStore(CDECollectionRecordMapper recordMapper, CDEProjectMapper projectMapper) {
        this.recordMapper = recordMapper;
        this.projectMapper = projectMapper;
    }

    public void initData() {
        store.clear();
        for (CDECollectionRecord record : recordMapper.selectList(null)) {
            store.put(record.getRegistrationNumber(), record);
        }
    }

    public boolean exist(String registrationNumber) {
        return !store.containsKey(registrationNumber);
    }

    public CDECollectionRecord get(String registrationNumber) {
        return store.get(registrationNumber);
    }

    public List<CDECollectionRecord> getNotDownloadProjects() {
        return store.values().stream().filter(record -> !record.getIsDownload()).toList();
    }

    public List<CDECollectionRecord> getNotParseProjects() {
        return store.values().stream().filter(record -> !record.getIsParse()).toList();
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

        CDEProject oldProject = projectMapper.selectOne(Wrappers.<CDEProject>lambdaQuery().eq(CDEProject::getRegistrationNumber, record.getRegistrationNumber()));
        if (oldProject == null) {
            projectMapper.insert(project);
        } else {
            project.setUid(oldProject.getUid());
            projectMapper.updateById(project);
        }
        store.replace(registrationNumber, record);
    }

    public void add(CDECollectionRecord record) {
        if (store.containsKey(record.getRegistrationNumber())) {
            recordMapper.updateById(record);
        } else {
            record.setIsParse(false);
            record.setIsDownload(false);
            recordMapper.insert(record);
            store.put(record.getRegistrationNumber(), record);
        }
    }
}
