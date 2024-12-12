package com.onework.boot.scrape.cde;

import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.TaskServer;
import com.onework.boot.scrape.TaskServerType;
import com.onework.boot.scrape.WebDriverFactory;
import com.onework.boot.scrape.cde.config.CDEProjectFileConfiguration;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.data.entity.CDECollectionRecord;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  检索未下载文件记录，进行文件下载
 */
@Slf4j
@Component
public class CDEProjectFileTaskServer extends TaskServer {

    private final CDEProjectRecordStore cdeProjectRecordStore;

    private final CDEProjectFileConfiguration configuration;

    public CDEProjectFileTaskServer(CDEProjectRecordStore cdeProjectRecordStore, CDEProjectFileConfiguration configuration) {
        this.cdeProjectRecordStore = cdeProjectRecordStore;
        this.configuration = configuration;
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CDE_PROJECT_FILE;
    }

    @Override
    protected void beforeStart() {
        cdeProjectRecordStore.initData();
    }

    @Override
    public void run() {
        List<CDECollectionRecord> records = cdeProjectRecordStore.getNotDownloadProjects();
        String desc = getTaskServerType().getDescription();
        log.info("[{}],共有{}页", desc, records.size());

        int threadCount = configuration.getThreadCount();
        ScrapeHelper.listWorkerExecute(records, threadCount, (start, end, items) -> ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
            webDriver.get(configuration.getUrl());
            for (int i = 1; i <= items.size(); i++) {
                CDECollectionRecord record = items.get(i - 1);
                // 输入登记号
                WebElement input = ScrapeHelper.waitElement(webDriver, "input[class$='subSearchInput']");
                input.clear();
                input.sendKeys(record.getRegistrationNumber());
                // 点击搜索
                ScrapeHelper.buttonElement(webDriver, "button[onclick='searchList()']");
                String selector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > a";
                if (!ScrapeHelper.existElement(webDriver, selector)) {
                    log.info("[{}],第{}项,搜索登记号:{},不存在", desc, start + i, record.getRegistrationNumber());
                    continue;
                }
                // 点击链接跳转详情
                ScrapeHelper.buttonElement(webDriver, selector);
                // 切换详情页面
                ScrapeHelper.switchLastTab(webDriver, false);
                String noSelector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(1) > div:nth-of-type(2) > div > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1)";
                // 保存文件
                String registrationNo = ScrapeHelper.getText(webDriver, noSelector);
                String filePathName = String.format("%s\\%s.html", configuration.getSavePath(), registrationNo);
                ScrapeHelper.savePage(webDriver, filePathName);
                // 保存记录
                try {
                    cdeProjectRecordStore.markDownload(record.getRegistrationNumber(), filePathName);
                    log.info("[{}],第{}项,登记号:{},保存成功", desc, start + i, registrationNo);
                } catch (Exception exception) {
                    log.warn("[{}],第{}项,登记号:{},记录异常,错误消息:{}", desc, start + i, registrationNo, exception.getMessage());
                }
                // 切换回列表标签，关闭其他标签
                ScrapeHelper.switchFirstTab(webDriver, true);
            }
            return true;
        }));
    }
}
