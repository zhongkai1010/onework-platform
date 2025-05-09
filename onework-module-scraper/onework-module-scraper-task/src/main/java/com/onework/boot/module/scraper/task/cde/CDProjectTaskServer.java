package com.onework.boot.module.scraper.task.cde;


import com.onework.boot.module.scraper.core.dal.dataobject.CDECollectionRecord;
import com.onework.boot.module.scraper.task.ScrapeHelper;
import com.onework.boot.module.scraper.task.TaskServer;
import com.onework.boot.module.scraper.task.TaskServerType;
import com.onework.boot.module.scraper.task.WebDriverFactory;
import com.onework.boot.module.scraper.task.cde.config.CDProjectConfiguration;
import com.onework.boot.module.scraper.task.cde.store.CDEProjectRecordStore;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  检索项目列表，记录项目数据
 */
@Slf4j
@Component
public class CDProjectTaskServer extends TaskServer {

    private final CDProjectConfiguration configuration;

    private final CDEProjectRecordStore cdeProjectRecordStore;

    public CDProjectTaskServer(CDProjectConfiguration configuration, CDEProjectRecordStore cdeProjectRecordStore) {
        this.configuration = configuration;
        this.cdeProjectRecordStore = cdeProjectRecordStore;
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CDE_PROJECT;
    }

    @Override
    protected void beforeStart() {
        cdeProjectRecordStore.initData();
    }

    @Override
    public void run() {
        int total = getProjectPageTotal();
        String desc = getTaskServerType().getDescription();
        log.info("[{}],共有{}页", desc, total);

        int threadCount = configuration.getThreadCount();
        // 最大页数少于总页数，采用总页数，防止区间线程处理失效
        int maxPage = configuration.getMaxPage();
        if (total >= maxPage) {
            total = maxPage;
        }

        ScrapeHelper.workerExecute(total, threadCount, (start, end) -> {
            log.info("[{}],[{}~{}],开始执行", desc, start, end);
            final int[] successCount = {start};
            ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), (webDriver) -> {
                webDriver.get(configuration.getUrl());
                for (int i = start; i <= end; i++) {
                    // 超过最大采集页数，退出循环
                    if (i >= configuration.getMaxPage()) {
                        log.info("[{}],[{}~{}],超过最大采集页数({}),退出循环", desc, start, end, configuration.getMaxPage());
                        break;
                    }
                    // 防止意外退出，从异常开始
                    if (i != successCount[0]) {
                        continue;
                    }
                    // 执行脚本跳转指定页码页面
                    String script = String.format("gotopage(%s)", successCount[0]);
                    String selector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(6) > div > i:nth-of-type(2)";
                    ScrapeHelper.executeScript(webDriver, script, (driver) -> ScrapeHelper.existElement(driver, selector));
                    // 获取表格数据
                    WebElement tbody = ScrapeHelper.waitVisible(webDriver, "tbody");
                    List<WebElement> trs = tbody.findElements(By.tagName("tr"));
                    for (int j = 0; j < trs.size(); j++) {
                        if (j == 0) {
                            continue;
                        }
                        WebElement tr = trs.get(j);
                        String registrationNo = tr.findElement(By.cssSelector("td:nth-child(2)")).getText();
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
                        record.setIsParse(false);
                        record.setIsDownload(false);
                        record.setRecordDate(LocalDateTime.now());
                        try {
                            cdeProjectRecordStore.addOrUpdate(record);
                            if (isNewRecord) {
                                log.info("[{}],第{}项,{},新增成功", desc, i, registrationNo);
                            } else {
                                log.info("[{}],第{}项,{},更新成功", desc, i, registrationNo);
                            }
                        } catch (Exception exception) {
                            log.warn("[{}],第{}项,{},记录异常,错误消息:{}", desc, i, registrationNo, exception.getMessage());
                        }
                    }
                    successCount[0] += 1;
                }
                return true;
            });
        });
    }

    /**
     * 创建 ChromeDriver 对象，打开链接，获取项目总数
     */
    private int getProjectPageTotal() {
        AtomicInteger total = new AtomicInteger();
        ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), (webDriver) -> {
            webDriver.get(configuration.getUrl());
            String selector = "html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(6) > div > i:nth-of-type(2)";
            String text = ScrapeHelper.getText(webDriver, selector);
            total.set(Integer.parseInt(text));
            return true;
        });
        return total.get();
    }
}
