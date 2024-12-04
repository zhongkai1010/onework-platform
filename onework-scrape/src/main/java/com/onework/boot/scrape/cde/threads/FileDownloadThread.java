package com.onework.boot.scrape.cde.threads;

import cn.hutool.core.io.FileUtil;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.cde.CDEWebDriverHelper;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.data.entity.CDECollectionRecord;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class FileDownloadThread extends Thread {
    private final int start;
    private final int end;
    private final ScrapeConfiguration scrapeConfiguration;
    private final CDEProjectRecordStore CDEProjectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public FileDownloadThread(ScrapeConfiguration scrapeConfiguration, CDEProjectRecordStore CDEProjectRecordStore, int start, int end) {
        this.start = start;
        this.end = end;
        this.scrapeConfiguration = scrapeConfiguration;
        this.CDEProjectRecordStore = CDEProjectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("采集所有CDE项目服务（AllProjectProcessServer），开始执行线程（{}-{}）", start, end);
        int errorCount = 0;
        int successCount = start;
        while (errorCount <= scrapeConfiguration.getMaxErrorCount()) {
            WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
            try {
                webDriver.get(scrapeConfiguration.getCdeCollectionUrl());
                CDEWebDriverHelper.goToDetails(webDriver);
                LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），正常打开详情页面", start, end);
                for (int i = start; i <= end; i++) {
                    if (start == successCount) { // 防止意外退出，从异常开始
                        CDEWebDriverHelper.goToPage(webDriver, successCount);
                        String registrationNo = CDEWebDriverHelper.getRegistrationNo(webDriver);
                        if (CDEProjectRecordStore.exist(registrationNo)) {
                            String html = webDriver.getPageSource();
                            String filePathName = String.format("%s\\%s.html", scrapeConfiguration.getCdeSavePath(), registrationNo);
                            FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);

                            CDECollectionRecord record = new CDECollectionRecord();
                            record.setRegistrationNumber(registrationNo);
                            record.setRecordDate(LocalDateTime.now());
                            record.setDeleted(true);
                            record.setDownloadDate(LocalDateTime.now());
                            record.setIsParse(false);
                            CDEProjectRecordStore.add(record);
                            LOG.info("启动采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），第{}项，{}，成功保存", start, end, i, filePathName);
                        }
                    }
                    successCount++;
                }
                webDriver.quit();
                LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），处理完成", start, end);
                break;
            } catch (Exception ex) {
                webDriver.quit();
                errorCount += 1;
                LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}）出现异常，错误消息：{}", start, end, ex.getMessage());
            } finally {
                webDriver.quit();
                errorCount += 1;
            }
        }
        if (errorCount > scrapeConfiguration.getMaxErrorCount()) {
            LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），超出最大异常次数，执行退出", start, end);
        }
    }
}
