package com.onework.boot.ctr.collection.threads;

import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.ProjectRecordStore;
import com.onework.boot.ctr.collection.ServerConfiguration;
import com.onework.boot.ctr.collection.ServiceHelper;
import com.onework.boot.data.entity.CTRCollectionRecord;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class ScanListThread extends Thread {
    private final int start;
    private final int end;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    private final ServerConfiguration serverConfiguration;

    private final ProjectRecordStore projectRecordStore;

    public ScanListThread(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore, int start, int end) {
        this.serverConfiguration = serverConfiguration;
        this.start = start;
        this.end = end;
        this.projectRecordStore = projectRecordStore;
    }

    public void run() {
        LOG.info("启动CTR项目列表检索线程（{} - {}）", start, end);
        try {
            int currentPage = start;
            ChromeDriver webDriver = ServiceHelper.getWebDriver(serverConfiguration);
            while (true) {
                webDriver.get(String.format("%s?page=%s", serverConfiguration.getCollectionUrl(), currentPage));
                try {
                    if (currentPage > end || currentPage > serverConfiguration.getMaxPage()) {
                        webDriver.quit();
                        break;
                    } else {
                        LOG.info("启动CTR项目列表检索线程（{} - {}），第{}页，开始处理", start, end, currentPage);
                        List<CTRCollectionRecord> records = ServiceHelper.getTableContext(webDriver);
                        for (CTRCollectionRecord record : records) {
                            if (projectRecordStore.exist(record.getRegistrationNumber())) {
                                record.setRecordDate(LocalDateTime.now());
                                projectRecordStore.add(record);
                                LOG.info("CTR项目列表检索线程（{} - {}），第{}页，新增{}记录", start, end, currentPage, record.getRegistrationNumber());
                            } else {
                                LOG.info("CTR项目列表检索线程（{} - {}），第{}页，{}，已存在", start, end, currentPage, record.getRegistrationNumber());
                            }
                        }
                        LOG.info("CTR项目列表检索线程（{} - {}），第{}页，处理完成", start, end, currentPage);
                        ServiceHelper.nextPage(webDriver);
                        currentPage += 1;
                    }
                } catch (WebDriverException exception) {
                    webDriver = ServiceHelper.getWebDriver(serverConfiguration);
                } catch (Exception exception) {
                    LOG.error("CTR项目列表检索线程（{} - {}）异常，退出浏览器，异常消息：{}", start, end, exception.getMessage());
                    webDriver.quit();
                    break;
                }
            }
            LOG.info("CTR项目列表检索线程（{} - {}）处理完成", start, end);
        } catch (Exception ex) {
            LOG.error("CTR项目列表检索线程（{} - {}）处理失败，错误：{}", start, end, ex.getMessage());
        }
    }
}
