package com.onework.boot.ctr.collection.thread;

import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.ServerConfiguration;
import com.onework.boot.ctr.collection.ServiceHelper;
import com.onework.boot.data.entity.CTRCollectionRecord;
import com.onework.boot.data.mapper.CTRCollectionRecordMapper;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FileDownloadThread extends Thread {
    private final int start;
    private final int end;
    private final ServerConfiguration serverConfiguration;
    private final List<CTRCollectionRecord> records;
    private final CTRCollectionRecordMapper recordMapper;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    public FileDownloadThread(List<CTRCollectionRecord> records, ServerConfiguration serverConfiguration, CTRCollectionRecordMapper recordMapper, int start, int end) {
        this.start = start;
        this.end = end;
        this.serverConfiguration = serverConfiguration;
        this.records = records;
        this.recordMapper = recordMapper;
    }

    public void run() {
        LOG.info("启动CTR项目文件下载线程（{} - {}）", start, end);
        ChromeDriver webDriver = ServiceHelper.getWebDriver(serverConfiguration);
        try {
            for (CTRCollectionRecord record : records) {
                ServiceHelper.saveProject(webDriver, serverConfiguration, record);
                recordMapper.updateById(record);
                LOG.info("CTR项目文件下载线程（{} - {}）,{}，下载成功", start, end, record.getFilePath());
            }
            LOG.info("CTR项目文件下载线程（{} - {}）处理完成", start, end);
        } catch (Exception exception) {
            webDriver.quit();
            LOG.error("CTR项目文件下载线程（{} - {}）异常，退出浏览器，异常消息：{}", start, end, exception.getMessage());
        } finally {
            webDriver.quit();
        }
    }
}
