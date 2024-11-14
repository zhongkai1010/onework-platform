package com.onework.collection.cde;

import cn.hutool.core.io.FileUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class CollectionThread extends Thread {
    private final int start;
    private final int end;
    private final ServerConfiguration serverConfiguration;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScraperCDEServerApplication.class);

    public CollectionThread(ServerConfiguration serverConfiguration, int start, int end) {
        this.start = start;
        this.end = end;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void run() {
        LOG.info("开始执行线程（{}-{}）", start, end);
        int errorCount = 0;
        while (errorCount <= serverConfiguration.getMaxErrorCount()) {
            try {
                WebDriver webDriver = ServiceHelper.getWebDriver(false);
                webDriver.get(serverConfiguration.getCollectionUrl());
                ServiceHelper.goToDetails(webDriver);
                ServiceHelper.goToPage(webDriver, start);
                for (int i = start; i <= end; i++) {
                    ServiceHelper.goToPage(webDriver, i);
                    String registrationNo = ServiceHelper.getRegistrationNo(webDriver);
                    String html = webDriver.getPageSource();
                    String filePathName = String.format("%s\\%s.html", serverConfiguration.getSavePath(), registrationNo);
                    FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
                    LOG.info("成功保存{}", filePathName);
                    Thread.sleep(2 * 1000);
                }
                webDriver.quit();
                LOG.info("处理完成线程（{}-{}）", start, end);
                break;
            } catch (Exception ex) {
                errorCount += 1;
                LOG.info("处理线程（{}-{}）异常，错误消息：{}", start, end, ex.getMessage());
            } finally {
                errorCount += 1;
            }
        }
        if (errorCount > serverConfiguration.getMaxErrorCount()) {
            LOG.info("处理线程（{}-{}）超出最大异常次数，执行退出", start, end);
        }
    }
}
