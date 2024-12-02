package com.onework.boot.scrape.ctr.threads;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ServerConfiguration;
import com.onework.boot.scrape.WebDriverHelper;
import com.onework.boot.scrape.ctr.CTRWebDriverHelper;
import com.onework.boot.scrape.ctr.store.CTRProjectRecordStore;
import com.onework.boot.scrape.data.entity.CTRCollectionRecord;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FileDownloadThread extends Thread {
    private final int start;
    private final int end;
    private final ServerConfiguration serverConfiguration;
    private final List<CTRCollectionRecord> records;
    private final CTRProjectRecordStore CTRProjectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public FileDownloadThread(List<CTRCollectionRecord> records, ServerConfiguration serverConfiguration, CTRProjectRecordStore CTRProjectRecordStore, int start, int end) {
        this.start = start;
        this.end = end;
        this.serverConfiguration = serverConfiguration;
        this.records = records;
        this.CTRProjectRecordStore = CTRProjectRecordStore;
    }

    public void run() {
        LOG.info("启动CTR项目文件下载线程（{} - {}）", start, end);
        ChromeDriver webDriver = WebDriverHelper.getWebDriver(serverConfiguration);
        for (CTRCollectionRecord record : records) {
            try {
                webDriver.get(record.getProjectLink());
                String path = CTRWebDriverHelper.saveProject(webDriver, serverConfiguration);
                CTRProjectRecordStore.markDownload(record.getRegistrationNumber(), path);
                LOG.info("CTR项目文件下载线程（{} - {}）,{}，下载成功", start, end, record.getFilePath());
            } catch (Exception exception) {
                webDriver.quit();
                LOG.error("CTR项目文件下载线程（{} - {}）异常，退出浏览器，异常消息：{}", start, end, exception.getMessage());
                webDriver = WebDriverHelper.getWebDriver(serverConfiguration);
            }
        }
        webDriver.quit();
        LOG.info("CTR项目文件下载线程（{} - {}）处理完成", start, end);
    }
}
