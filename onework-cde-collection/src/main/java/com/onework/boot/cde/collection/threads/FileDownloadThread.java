package com.onework.boot.cde.collection.threads;

import cn.hutool.core.io.FileUtil;
import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.ProjectRecordStore;
import com.onework.boot.cde.collection.ServerConfiguration;
import com.onework.boot.cde.collection.WebDriverHelper;
import com.onework.boot.data.entity.CDECollectionRecord;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class FileDownloadThread extends Thread {
    private final int start;
    private final int end;
    private final ServerConfiguration serverConfiguration;
    private final ProjectRecordStore projectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    public FileDownloadThread(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore, int start, int end) {
        this.start = start;
        this.end = end;
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("采集所有CDE项目服务（AllProjectProcessServer），开始执行线程（{}-{}）", start, end);
        int errorCount = 0;
        int successCount = start;
        while (errorCount <= serverConfiguration.getMaxErrorCount()) {
            WebDriver webDriver = WebDriverHelper.getWebDriver(serverConfiguration);
            try {
                webDriver.get(serverConfiguration.getCollectionUrl());
                WebDriverHelper.goToDetails(webDriver);
                LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），正常打开详情页面", start, end);
                for (int i = start; i <= end; i++) {
                    if (start == successCount) { // 防止意外退出，从异常开始
                        WebDriverHelper.goToPage(webDriver, successCount);
                        String registrationNo = WebDriverHelper.getRegistrationNo(webDriver);
                        if (projectRecordStore.exist(registrationNo)) {
                            String html = webDriver.getPageSource();
                            String filePathName = String.format("%s\\%s.html", serverConfiguration.getSavePath(), registrationNo);
                            FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);

                            CDECollectionRecord record = new CDECollectionRecord();
                            record.setRegistrationNumber(registrationNo);
                            record.setRecordDate(LocalDateTime.now());
                            record.setDeleted(true);
                            record.setDownloadDate(LocalDateTime.now());
                            record.setIsParse(false);
                            projectRecordStore.add(record);
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
        if (errorCount > serverConfiguration.getMaxErrorCount()) {
            LOG.info("采集所有CDE项目服务（AllProjectProcessServer），线程（{}-{}），超出最大异常次数，执行退出", start, end);
        }
    }
}
