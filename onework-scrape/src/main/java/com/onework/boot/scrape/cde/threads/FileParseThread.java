package com.onework.boot.scrape.cde.threads;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.cde.CDEParseHelper;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.data.entity.CDECollectionRecord;
import com.onework.boot.scrape.data.entity.CDEProject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class FileParseThread extends Thread {

    private final List<CDECollectionRecord> records;

    private final int start;

    private final int end;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final CDEProjectRecordStore CDEProjectRecordStore;

    public FileParseThread(int start, int end, List<CDECollectionRecord> records, CDEProjectRecordStore CDEProjectRecordStore) {
        this.records = records;
        this.start = start;
        this.end = end;
        this.CDEProjectRecordStore = CDEProjectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）],开始处理", start, end + 1);
        for (CDECollectionRecord record : records) {
            File file = new File(record.getFilePath());
            if (!file.exists()) {
                LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，{}文件不存在，", start, end, record.getFilePath());
            } else {
                try {
                    Document document = Jsoup.parse(file);
                    CDEProject project = CDEParseHelper.createProject(document);
                    CDEProjectRecordStore.markParse(record.getRegistrationNumber(), project);
                    LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，{}文件已经解析完成，", start, end, record.getFilePath());

                } catch (Exception exception) {
                    LOG.warn("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）]，解析{}文件异常，错误消息：{}", start, end, record.getFilePath(), exception.getMessage());
                }
            }
        }
        LOG.info("项目文件解析服务（FileParseProcessServer），[线程（{}-{}）],处理完成", start, end + 1);
    }
}
