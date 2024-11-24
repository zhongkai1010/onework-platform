package com.onework.boot.cde.collection.tasks;

import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.ProjectRecordStore;
import com.onework.boot.cde.collection.ServerConfiguration;
import com.onework.boot.cde.collection.WebDriverHelper;
import com.onework.boot.data.entity.CDECollectionRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  检索项目列表，记录项目数据
 */
@Component
public class ScanListTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    private final ServerConfiguration serverConfiguration;

    private final ProjectRecordStore projectRecordStore;

    public ScanListTaskServer(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("启动检索项目列表，记录项目数据服务（AllProjectProcessServer）");
        projectRecordStore.initData();
        try {
            WebDriver webDriver = WebDriverHelper.getWebDriver(serverConfiguration);
            webDriver.get(serverConfiguration.getCollectionUrl());
            int totalPage = WebDriverHelper.getProjectPageTotal(webDriver);
            for (int i = 1; i <= totalPage; i++) {
                LOG.info("启动检索项目列表，记录项目数据服务（AllProjectProcessServer），处理第{}页", i);
                try {
                    // 超过最大采集页数，退出循环
                    if (i > serverConfiguration.getMaxPage()) {
                        break;
                    }
                    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); // 设置等待时间为5秒
                    WebElement tbodyWebElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tbody")));
                    List<WebElement> trsWebElement = tbodyWebElement.findElements(By.cssSelector("tr"));
                    for (int j = 0; j < trsWebElement.size(); j++) {
                        if (j == 0) {
                            continue;
                        }
                        WebElement trWebElement = trsWebElement.get(j);
                        String registrationNumber = trWebElement.findElement(By.cssSelector("td:nth-child(2)")).getText();
                        if (projectRecordStore.exist(registrationNumber)) {

                            CDECollectionRecord record = new CDECollectionRecord();
                            record.setRegistrationNumber(registrationNumber);
                            record.setRecordDate(LocalDateTime.now());
                            projectRecordStore.add(record);
                            LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer），第{}页，发现新登记号：{}", i, registrationNumber);
                        } else {
                            LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer），第{}页，登记号：{}，已存在", i, registrationNumber);
                        }
                    }
                    JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                    executor.executeScript("gotopage(" + (i + 1) + ")");
                } catch (Exception exception) {
                    webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
                    LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer），第{}页，处理异常，错误消息：{}", i, exception.getMessage());
                }
            }
            webDriver.quit();
        } catch (Exception exception) {
            LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer）异常，错误消息:{}", exception.getMessage());
        }
    }
}
