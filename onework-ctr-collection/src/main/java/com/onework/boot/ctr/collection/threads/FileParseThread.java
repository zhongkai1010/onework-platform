package com.onework.boot.ctr.collection.threads;

import com.onework.boot.ctr.collection.FileParseHelper;
import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.ProjectRecordStore;
import com.onework.boot.data.entity.CDEProject;
import com.onework.boot.data.entity.CTRCollectionRecord;
import com.onework.boot.data.entity.CTRProject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class FileParseThread extends Thread {

    private final List<CTRCollectionRecord> records;

    private final int start;

    private final int end;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    private final ProjectRecordStore projectRecordStore;

    public FileParseThread(int start, int end, List<CTRCollectionRecord> records, ProjectRecordStore projectRecordStore) {
        this.records = records;
        this.start = start;
        this.end = end;
        this.projectRecordStore = projectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）],开始处理", start, end + 1);
        for (CTRCollectionRecord record : records) {
            File file = new File(record.getFilePath());
            if (!file.exists()) {
                LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，{}文件不存在，", start, end, record.getFilePath());
            } else {
                try {
                    Document document = Jsoup.parse(file);
                    CTRProject project = FileParseHelper.createProject(document);

                    projectRecordStore.markParse(record.getRegistrationNumber(), project);
                    LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，{}文件已经解析完成，", start, end, record.getFilePath());

                } catch (Exception exception) {
                    LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，解析{}文件异常，错误消息：{}", start, end, record.getFilePath(), exception.getMessage());
                }
            }
        }
        LOG.info("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）],处理完成", start, end + 1);
    }
}
