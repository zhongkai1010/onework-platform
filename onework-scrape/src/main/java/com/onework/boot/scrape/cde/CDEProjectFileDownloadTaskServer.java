package com.onework.boot.scrape.cde;

import cn.hutool.core.io.FileUtil;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.data.entity.CDECollectionRecord;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *  检索未下载文件记录，进行文件下载
 */
@Component
public class CDEProjectFileDownloadTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final CDEProjectRecordStore cdeProjectRecordStore;

    private final ScrapeConfiguration scrapeConfiguration;

    public CDEProjectFileDownloadTaskServer(CDEProjectRecordStore cdeProjectRecordStore, ScrapeConfiguration scrapeConfiguration) {
        this.cdeProjectRecordStore = cdeProjectRecordStore;
        this.scrapeConfiguration = scrapeConfiguration;

        this.cdeProjectRecordStore.initData();
    }

    @Override
    public void run() {
        LOG.info("启动下载项目文件服务（FileDownloadProcessServer）");

        List<CDECollectionRecord> records = cdeProjectRecordStore.getNotDownloadProjects();
        if (records.isEmpty()) {
            LOG.info("下载项目文件服务（FileDownloadProcessServer），无最新记录，退出服务");
        } else {
            LOG.info("下载项目文件服务（FileDownloadProcessServer），共{}条，开始处理", records.size());
            while (true) {
                WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
                try {
                    webDriver.get(scrapeConfiguration.getCdeCollectionUrl());
                    LOG.info("下载项目文件服务（FileDownloadProcessServer），共{}条，成功打开链接", records.size());
                    for (CDECollectionRecord record : records) {
                        CDEWebDriverHelper.searchRegistrationNo(webDriver, record.getRegistrationNumber());
                        CDEWebDriverHelper.goToDetails(webDriver);
                        String registrationNo = CDEWebDriverHelper.getRegistrationNo(webDriver);
                        String html = webDriver.getPageSource();
                        String filePathName = String.format("%s\\%s.html", scrapeConfiguration.getCdeSavePath(), registrationNo);
                        FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
                        cdeProjectRecordStore.markDownload(record.getRegistrationNumber(), filePathName);
                        CDEWebDriverHelper.backListPage(webDriver);
                        LOG.info("下载项目文件服务（FileDownloadProcessServer），{}，下载成功", record.getFilePath());
                    }
                    webDriver.quit();
                    break;
                } catch (Exception exception) {
                    webDriver.quit();
                    LOG.info("下载项目文件服务（FileDownloadProcessServer），出现异常，错误消息：{}", exception.getMessage());
                } finally {
                    webDriver.quit();
                }
            }
        }
        LOG.info("下载项目文件服务（FileDownloadProcessServer），执行完成，退出服务");
    }
}
