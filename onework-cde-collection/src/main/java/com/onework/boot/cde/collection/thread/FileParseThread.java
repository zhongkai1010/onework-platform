package com.onework.boot.cde.collection.thread;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.cde.collection.FileParseHelper;
import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.entity.CDEProject;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import com.onework.boot.data.mapper.CDEProjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class FileParseThread extends Thread {

    private final List<CDECollectionRecord> records;

    private final int start;

    private final int end;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    private final CDEProjectMapper projectMapper;

    private final CDECollectionRecordMapper recordMapper;

    public FileParseThread(List<CDECollectionRecord> records, int start, int end, CDEProjectMapper projectMapper, CDECollectionRecordMapper recordMapper) {
        this.records = records;
        this.start = start;
        this.end = end;
        this.projectMapper = projectMapper;
        this.recordMapper = recordMapper;
    }

    @Override
    public void run() {
        for (CDECollectionRecord record : records) {
            File file = new File(record.getFilePath());
            if (!file.exists()) {
                LOG.warn("[线程（{}-{}）]，{}文件不存在，", start, end, record.getFilePath());
            } else {
                try {
                    Document document = Jsoup.parse(file);
                    CDEProject project = projectMapper.selectOne(Wrappers.<CDEProject>lambdaQuery().eq(CDEProject::getRegistrationNumber, record.getRegistrationNumber()));
                    if (project == null) {
                        project = FileParseHelper.createProject(document);
                        projectMapper.insert(project);
                    }
                    record.setParseHandle(true);
                    record.setParseDate(LocalDateTime.now());
                    recordMapper.updateById(record);
                    LOG.warn("[线程（{}-{}）]，{}文件已经解析完成，", start, end, record.getFilePath());

                } catch (Exception exception) {
                    LOG.warn("[线程（{}-{}）]，解析{}文件异常，错误消息：{}", start, end, record.getFilePath(), exception.getMessage());
                }
            }
        }
        LOG.warn("[线程（{}-{}）]，线程处理完成，", start, end);
    }
}
