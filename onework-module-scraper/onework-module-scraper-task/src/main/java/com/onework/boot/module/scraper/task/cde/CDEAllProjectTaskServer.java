package com.onework.boot.module.scraper.task.cde;


import com.onework.boot.module.scraper.core.dal.dataobject.CDECollectionRecord;
import com.onework.boot.module.scraper.task.ScrapeHelper;
import com.onework.boot.module.scraper.task.TaskServer;
import com.onework.boot.module.scraper.task.TaskServerType;
import com.onework.boot.module.scraper.task.WebDriverFactory;
import com.onework.boot.module.scraper.task.cde.config.CDEAllProjectConfiguration;
import com.onework.boot.module.scraper.task.cde.store.CDEProjectRecordStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  下载所有项目文件
 */
@Slf4j
@Component
public class CDEAllProjectTaskServer extends TaskServer {

    private final CDEProjectRecordStore cdeProjectRecordStore;

    private final CDEAllProjectConfiguration configuration;

    public CDEAllProjectTaskServer(CDEProjectRecordStore cdeProjectRecordStore, CDEAllProjectConfiguration configuration) {
        this.cdeProjectRecordStore = cdeProjectRecordStore;
        this.configuration = configuration;
    }

    @Override
    protected void beforeStart() {
        cdeProjectRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CDE_All_PROJECT;
    }

    @Override
    public void run() {
        int total = getProjectTotal();
        String desc = getTaskServerType().getDescription();
        log.info("[{}],共有{}项", desc, total);
        int threadCount = configuration.getThreadCount();
        ScrapeHelper.workerExecute(total, threadCount, (start, end) -> {
            log.info("[{}],[{}~{}],开始执行", desc, start, end);
            final int[] successCount = {start};
            ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), (webDriver) -> {
                webDriver.get(configuration.getUrl());
                // 打开项目详情页，通过详情页“下一个项目”进行操作，关闭之前页面
                String selector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > a";
                ScrapeHelper.clickElement(webDriver, selector);
                //关闭其他标签，切换详情页标签
                ScrapeHelper.switchLastTab(webDriver, true);
                for (int i = start; i < end; i++) {
                    if (i != successCount[0]) { // 防止意外退出，从异常开始
                        continue;
                    }
                    // 获取登记号
                    String noSelector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(1) > div:nth-of-type(2) > div > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1)";
                    String registrationNo = ScrapeHelper.getText(webDriver, noSelector);
                    // 保存文件
                    String filePathName = String.format("%s\\%s.html", configuration.getSavePath(), registrationNo);
                    ScrapeHelper.savePage(webDriver, filePathName);
                    // 保存记录
                    CDECollectionRecord record;
                    boolean isNewRecord = false;
                    if (cdeProjectRecordStore.isExist(registrationNo)) {
                        record = cdeProjectRecordStore.get(registrationNo);
                    } else {
                        isNewRecord = true;
                        record = new CDECollectionRecord();
                        record.setRegistrationNumber(registrationNo);
                    }
                    record.setRecordDate(LocalDateTime.now());
                    record.setIsDownload(true);
                    record.setFilePath(filePathName);
                    record.setDownloadDate(LocalDateTime.now());
                    record.setIsParse(false);
                    try {
                        cdeProjectRecordStore.addOrUpdate(record);
                        if (isNewRecord) {
                            log.info("[{}],第{}项,{},新增成功", desc, i, registrationNo);
                        } else {
                            log.info("[{}],第{}项,{},更新成功", desc, i, registrationNo);
                        }
                    } catch (Exception exception) {
                        log.warn("[{}], 第{}项,{},记录异常,错误消息:{}", desc, i, registrationNo, exception.getMessage());
                    }
                    // 跳转下一个项目
                    String script = String.format("gotopage(%s)", i + 1);
                    ScrapeHelper.executeScript(webDriver, script);
                    successCount[0] += 1;
                }
                log.info("[{}],线程({}~{}),执行完成", desc, start, end);
                return true;
            });
        });
    }

    /**
     * 创建 ChromeDriver 对象，打开链接，获取项目总数
     */
    private int getProjectTotal() {
        AtomicInteger total = new AtomicInteger();
        ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), (webDriver) -> {
            webDriver.get(configuration.getUrl());
            String selector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(6) > div > i:nth-of-type(3)";
            String text = ScrapeHelper.getText(webDriver, selector);
            total.set(Integer.parseInt(text));
            return true;
        });
        return total.get();
    }
}
