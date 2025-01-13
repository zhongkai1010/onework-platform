package com.onework.boot.scrape.site.ctr;

import com.onework.boot.scrape.dal.dataobject.CTRCollectionRecord;
import com.onework.boot.scrape.site.ScrapeHelper;
import com.onework.boot.scrape.site.TaskServer;
import com.onework.boot.scrape.site.TaskServerType;
import com.onework.boot.scrape.site.WebDriverFactory;
import com.onework.boot.scrape.site.ctr.config.CTRProjectConfiguration;
import com.onework.boot.scrape.site.ctr.store.CTRProjectRecordStore;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class CTRProjectTaskServer extends TaskServer {

    private final CTRProjectRecordStore ctrProjectRecordStore;

    private final CTRProjectConfiguration configuration;

    public CTRProjectTaskServer(CTRProjectRecordStore ctrProjectRecordStore, CTRProjectConfiguration configuration) {
        this.ctrProjectRecordStore = ctrProjectRecordStore;
        this.configuration = configuration;
    }

    @Override
    protected void beforeStart() {
        ctrProjectRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CTR_PROJECT;
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();

        AtomicInteger totalPage = new AtomicInteger();
        ScrapeHelper.continueExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
            webDriver.get(configuration.getUrl());
            String selector = "html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > ul > li:nth-of-type(15)";
            String text = ScrapeHelper.getText(webDriver, selector);
            totalPage.set(getNum(text));
            return true;
        }, webDriver -> ScrapeHelper.switchLastTab(webDriver, false));
        log.info("[{}],共有{}条", desc, totalPage.get());

        int threadCount = configuration.getThreadCount();
        // 最大页数少于总页数，采用总页数，防止区间线程处理失效
        int maxPage = configuration.getMaxPage();
        if (totalPage.get() <= maxPage) {
            totalPage.set(maxPage);
        }
        ScrapeHelper.workerExecute(totalPage.get(), threadCount, (start, end) -> {
            final int[] successCount = {start};
            ScrapeHelper.continueExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
                for (int i = start; i <= end; i++) {
                    if (i >= configuration.getMaxPage()) {
                        log.info("[{}],[{}~{}],超过最大采集页数({}),退出循环", desc, start, end, configuration.getMaxPage());
                        return true;
                    }
                    // 防止意外退出，从异常开始
                    if (i != successCount[0]) {
                        continue;
                    }
                    webDriver.get(String.format("%s?page=%s", configuration.getUrl(), i));
                    WebElement table = ScrapeHelper.waitVisible(webDriver, "table[border='0']");
                    List<WebElement> trsWebElement = table.findElements(By.tagName("tr"));
                    for (int j = 0; j < trsWebElement.size(); j++) {
                        if (j == 0) {
                            continue;
                        }
                        WebElement tr = trsWebElement.get(j);
                        String registrationNumber = ScrapeHelper.getText(tr, "td:nth-of-type(2)");
                        CTRCollectionRecord record;
                        boolean isNew = false;
                        if (ctrProjectRecordStore.isExist(registrationNumber)) {
                            record = ctrProjectRecordStore.get(registrationNumber);
                            if (configuration.isReset()) {
                                record.setIsDownload(false);
                                record.setIsParse(false);
                            }
                        } else {
                            isNew = true;
                            record = new CTRCollectionRecord();
                            record.setIsParse(false);
                            record.setIsDownload(false);
                        }
                        parseRecord(tr, record);
                        try {
                            ctrProjectRecordStore.addOrUpdate(record);
                            // log.info("{}", JSON.toJSONString(record));
                            log.info("[{}],第{}条,{},{}成功", desc, start + i, record.getRegistrationNumber(), isNew ? "新增记录" : "更新记录");
                        } catch (Exception exception) {
                            log.info("[{}],第{}条,{},记录失败,错误消息:{}", desc, start + i, record.getRegistrationNumber(), exception.getMessage());
                        }
                    }
                    successCount[0] += 1;
                }
                return true;
            }, webDriver -> ScrapeHelper.switchLastTab(webDriver, false));
        });
    }

    private void parseRecord(WebElement tr, CTRCollectionRecord record) {
        String historyVersionLink = ScrapeHelper.getAttributeValue(tr, "td:nth-of-type(1) > a", "href");
        record.setHistoryVersionLink(historyVersionLink);

        String registrationNumber = ScrapeHelper.getText(tr, " td:nth-of-type(2)");
        record.setRegistrationNumber(registrationNumber);

        String title = ScrapeHelper.getAttributeValue(tr, "td:nth-of-type(3) > a", "title");
        record.setRegistrationTitle(title);

        String hospital = ScrapeHelper.getText(tr, "td:nth-of-type(3) > p");
        record.setApplicantInstitution(hospital);

        String type = ScrapeHelper.getText(tr, "td:nth-of-type(4)");
        record.setStudyType(type);

        String date = ScrapeHelper.getText(tr, "td:nth-of-type(5)");
        record.setRegistrationTime(ScrapeHelper.getTryLocalDateTime(date, "yyyy/MM/dd"));

        String url = ScrapeHelper.getAttributeValue(tr, "td:nth-of-type(3) > a", "href");
        record.setProjectLink(url);
    }

    /**
     *  提取文本数字
     * @param text 文本
     * @return 返回数字
     */
    private static int getNum(String text) {
        Pattern pattern = Pattern.compile("\\d+"); // 匹配数字
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String number = matcher.group();
            return Integer.parseInt(number);
        } else {
            return 0;
        }
    }
}
