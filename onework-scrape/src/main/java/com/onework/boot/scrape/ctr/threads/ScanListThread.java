package com.onework.boot.scrape.ctr.threads;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.ctr.CTRWebDriverHelper;
import com.onework.boot.scrape.ctr.store.CTRProjectRecordStore;
import com.onework.boot.scrape.data.entity.CTRCollectionRecord;
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
            .getLogger(OneworkScrapeApplication.class);

    private final ScrapeConfiguration scrapeConfiguration;

    private final CTRProjectRecordStore CTRProjectRecordStore;

    public ScanListThread(ScrapeConfiguration scrapeConfiguration, CTRProjectRecordStore CTRProjectRecordStore, int start, int end) {
        this.scrapeConfiguration = scrapeConfiguration;
        this.start = start;
        this.end = end;
        this.CTRProjectRecordStore = CTRProjectRecordStore;
    }

    public void run() {
        LOG.info("启动CTR项目列表检索线程（{} - {}）", start, end);
        try {
            int currentPage = start;
            ChromeDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
            while (true) {
                webDriver.get(String.format("%s?page=%s", scrapeConfiguration.getCtrCollectionUrl(), currentPage));
                try {
                    if (currentPage > end || currentPage > scrapeConfiguration.getMaxPage()) {
                        webDriver.quit();
                        break;
                    } else {
                        LOG.info("启动CTR项目列表检索线程（{} - {}），第{}页，开始处理", start, end, currentPage);
                        List<CTRCollectionRecord> records = CTRWebDriverHelper.getTableContext(webDriver);
                        for (CTRCollectionRecord record : records) {
                            if (CTRProjectRecordStore.exist(record.getRegistrationNumber())) {
                                record.setRecordDate(LocalDateTime.now());
                                CTRProjectRecordStore.add(record);
                                LOG.info("CTR项目列表检索线程（{} - {}），第{}页，新增{}记录", start, end, currentPage, record.getRegistrationNumber());
                            } else {
                                LOG.info("CTR项目列表检索线程（{} - {}），第{}页，{}，已存在", start, end, currentPage, record.getRegistrationNumber());
                            }
                        }
                        LOG.info("CTR项目列表检索线程（{} - {}），第{}页，处理完成", start, end, currentPage);
                        CTRWebDriverHelper.nextPage(webDriver);
                        currentPage += 1;
                    }
                } catch (WebDriverException exception) {
                    webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
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
