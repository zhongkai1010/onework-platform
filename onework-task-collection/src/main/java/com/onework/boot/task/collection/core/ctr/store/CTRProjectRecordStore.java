package com.onework.boot.task.collection.core.ctr.store;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.task.collection.core.BaseStore;
import com.onework.boot.task.collection.dao.entity.CTRCollectionRecord;
import com.onework.boot.task.collection.dao.entity.CTRProject;
import com.onework.boot.task.collection.dao.mapper.CTRCollectionRecordMapper;
import com.onework.boot.task.collection.dao.mapper.CTRProjectMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CTRProjectRecordStore extends BaseStore {

    private final CTRCollectionRecordMapper mapper;

    private final CTRProjectMapper projectMapper;

    private final ConcurrentHashMap<String, CTRCollectionRecord> store = new ConcurrentHashMap<>();

    public CTRProjectRecordStore(CTRCollectionRecordMapper mapper, CTRProjectMapper projectMapper) {
        this.mapper = mapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public void initData() {
        store.clear();
        for (CTRCollectionRecord record : mapper.selectList()) {

            store.put(record.getRegistrationNumber(), record);
        }
    }

    public boolean isExist(String registrationNumber) {
        return store.containsKey(registrationNumber);
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

    public void markDownload(@NotNull String registrationNumber, @NotNull String path) {
        CTRCollectionRecord record = store.get(registrationNumber);
        record.setFilePath(path);
        record.setIsDownload(true);
        record.setDownloadDate(LocalDateTime.now());
        mapper.updateById(record);
        store.replace(registrationNumber, record);
    }

    public void markParse(String registrationNumber, CTRProject project) {
        CTRCollectionRecord record = store.get(registrationNumber);
        record.setIsParse(true);
        record.setParseDate(LocalDateTime.now());
        mapper.updateById(record);
        // 保存项目
        CTRProject oldProject = projectMapper.selectOne(Wrappers.<CTRProject>lambdaQuery().eq(CTRProject::getRegistrationNumber, record.getRegistrationNumber()));
        if (oldProject == null) {
            projectMapper.insert(project);
        } else {
            project.setUid(oldProject.getUid());
            projectMapper.updateById(project);
        }
        store.replace(registrationNumber, record);
    }

    public void addOrUpdate(CTRCollectionRecord record) {
        if (store.containsKey(record.getRegistrationNumber())) {
            mapper.updateById(record);
            store.replace(record.getRegistrationNumber(), record);
        } else {
            mapper.insert(record);
            store.put(record.getRegistrationNumber(), record);
        }
    }
}