package com.onework.boot.cde.collection.thread;

import cn.hutool.core.io.FileUtil;
import com.onework.boot.cde.collection.CollectionHelper;
import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.RegistrationNumberStore;
import com.onework.boot.cde.collection.ServerConfiguration;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class CollectionProjectThread extends Thread {
    private final int start;
    private final int end;
    private final ServerConfiguration serverConfiguration;
    private final RegistrationNumberStore registrationNumberStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    public CollectionProjectThread(ServerConfiguration serverConfiguration, RegistrationNumberStore registrationNumberStore, int start, int end) {
        this.start = start;
        this.end = end;
        this.serverConfiguration = serverConfiguration;
        this.registrationNumberStore = registrationNumberStore;
    }

    @Override
    public void run() {
        LOG.info("开始执行线程（{}-{}）", start, end);
        int errorCount = 0;
        while (errorCount <= serverConfiguration.getMaxErrorCount()) {
            WebDriver webDriver = CollectionHelper.getWebDriver(serverConfiguration.isOpenWindow(), serverConfiguration.isIncognito());
            try {
                webDriver.get(serverConfiguration.getCollectionUrl());
                CollectionHelper.goToDetails(webDriver);
                CollectionHelper.goToPage(webDriver, start);
                LOG.info("开始执行线程（{}-{}），正常打开详情页面", start, end);
                for (int i = start; i <= end; i++) {
                    CollectionHelper.goToPage(webDriver, i);
                    String registrationNo = CollectionHelper.getRegistrationNo(webDriver);
                    if (registrationNumberStore.exist(registrationNo)) {
                        String html = webDriver.getPageSource();
                        String filePathName = String.format("%s\\%s.html", serverConfiguration.getSavePath(), registrationNo);
                        FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
                        registrationNumberStore.add(registrationNo, filePathName);
                        LOG.info("[线程（{}-{}）]，第{}项，文件：{}，成功保存", start, end, i, filePathName);
                    } else {
                        LOG.info("[线程（{}-{}）]，第{}项，登记号：{}，已存在，不进行处理", start, end, i, registrationNo);
                    }
                }
                webDriver.quit();
                LOG.info("[线程（{}-{}）] 处理完成", start, end);
                break;
            } catch (Exception ex) {
                webDriver.quit();
                errorCount += 1;
                LOG.info("[线程（{}-{}）] 出现异常，错误消息：{}", start, end, ex.getMessage());
            } finally {
                webDriver.quit();
                errorCount += 1;
            }
        }
        if (errorCount > serverConfiguration.getMaxErrorCount()) {
            LOG.info("处理线程（{}-{}）超出最大异常次数，执行退出", start, end);
        }
    }
}
