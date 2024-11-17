package com.onework.boot.ctr.collection;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.onework.boot.data.entity.CTRCollectionRecord;
import com.onework.boot.data.mapper.CTRCollectionRecordMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationNumberStore {

    private final CTRCollectionRecordMapper recordMapper;

    private final List<String> store = new ArrayList<>();

    private final SqlSessionFactory sqlSessionFactory;

    public RegistrationNumberStore(CTRCollectionRecordMapper recordMapper, SqlSessionFactory sqlSessionFactory) {
        this.recordMapper = recordMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void initData() {
        for (CTRCollectionRecord record : recordMapper.selectList(null)) {
            store.add(record.getRegistrationNumber());
        }
    }

    public boolean exist(String registrationNumber) {
        return !store.contains(registrationNumber);
    }

    public void add(CTRCollectionRecord record) {
        recordMapper.insert(record);
        store.add(record.getRegistrationNumber());
    }

    public void batchAdd(List<CTRCollectionRecord> records) {
        MybatisBatch<CTRCollectionRecord> mybatisBatch = new MybatisBatch<>(sqlSessionFactory, records);
        MybatisBatch.Method<CTRCollectionRecord> method = new MybatisBatch.Method<>(CTRCollectionRecordMapper.class);
        mybatisBatch.execute(method.insert());
        for (CTRCollectionRecord record : records) {
            store.add(record.getRegistrationNumber());
        }
    }
}
