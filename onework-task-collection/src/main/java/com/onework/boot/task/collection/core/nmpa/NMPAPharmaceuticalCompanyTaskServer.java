package com.onework.boot.task.collection.core.nmpa;

import cn.hutool.core.util.NumberUtil;
import com.onework.boot.task.collection.core.ScrapeHelper;
import com.onework.boot.task.collection.core.TaskServer;
import com.onework.boot.task.collection.core.TaskServerType;
import com.onework.boot.task.collection.core.WebDriverFactory;
import com.onework.boot.task.collection.core.nmpa.config.NMPAPharmaceuticalCompanyConfiguration;
import com.onework.boot.task.collection.core.nmpa.store.NMPACollectionRecordStore;
import com.onework.boot.task.collection.dao.entity.NMPACollectionRecord;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class NMPAPharmaceuticalCompanyTaskServer extends TaskServer {

    private final NMPACollectionRecordStore store;

    private final NMPAPharmaceuticalCompanyConfiguration configuration;

    public NMPAPharmaceuticalCompanyTaskServer(NMPACollectionRecordStore store, NMPAPharmaceuticalCompanyConfiguration configuration) {
        this.store = store;
        this.configuration = configuration;
    }

    @Override
    protected void beforeStart() {
        store.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.NMP_PHARMACEUTICAL_COMPANY;
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();
        // 获取分页
        AtomicInteger atomicInteger = new AtomicInteger();
        ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
            webDriver.get(configuration.getUrl());
            log.info("页面已加载完成");
            goToListPage(webDriver, "获取分页总数");
            String text = ScrapeHelper.getText(webDriver, "html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(3) > div > div > span:nth-of-type(1)");
            int total = getNum(text);
            atomicInteger.set(NumberUtil.round(total / 10.0, 0, RoundingMode.HALF_UP).intValue());
            return true;
        });
        int pageTotal = atomicInteger.get();
        log.info("[{}],共有{}页", desc, pageTotal);
        // 最大页数少于总页数，采用总页数，防止区间线程处理失效
        int maxPage = configuration.getMaxPage();
        if (pageTotal >= maxPage) {
            pageTotal = maxPage;
        }
        // 多线程处理
        int threadCount = configuration.getThreadCount();
        ScrapeHelper.workerExecute(pageTotal, threadCount, (start, end) -> {
            String logMsg = String.format("[%s],[%s,%s]", desc, start, end);
            log.info("{},开始执行", logMsg);
            final int[] successCount = {start};
            ScrapeHelper.loopExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
                webDriver.get(configuration.getUrl());
                // 跳转列表页面
                goToListPage(webDriver, logMsg);
                // 跳转指定页面
                WebElement pageInput = ScrapeHelper.waitVisible(webDriver, "input[min='1']");
                pageInput.clear();
                pageInput.sendKeys(String.valueOf(successCount[0]) + Keys.ENTER);
                String upCompanyName = "";
                for (int i = successCount[0]; i <= end; i++) {
                    WebElement tbody = ScrapeHelper.waitVisible(webDriver, "tbody");
                    List<WebElement> trs = tbody.findElements(By.cssSelector("tr"));
                    for (int j = 0; j < trs.size(); j++) {
                        WebElement tr = trs.get(j);
                        try {
                            String companyName = ScrapeHelper.getText(tr, " td:nth-of-type(2) > div");
                            if (j == 0) {
                                upCompanyName = companyName;
                            }
                            String licenseNumber = ScrapeHelper.getText(tr, "td:nth-of-type(3) > div");
                            String socialCreditCode = ScrapeHelper.getText(tr, "td:nth-of-type(4) > div");
                            WebElement button = tr.findElement(By.cssSelector("td:nth-of-type(5) > div > button"));
                            String url = null;
                            button.click();
                            // log.warn("点击第{}项详情按钮，", j + 1);
                            ScrapeHelper.switchLastTab(webDriver, false);
                            // log.warn("切换第{}项详情页", j + 1);
                            try {
                                new WebDriverWait(webDriver, Duration.ofMinutes(1)).until(ExpectedConditions.urlContains("www.nmpa.gov.cn"));
                                url = webDriver.getCurrentUrl();
                            } catch (Exception ignored) {
                            }
                            // log.info("序号:{},企业名称:{},许可证编号:{},社会信用代码:{},链接:{}", serialNumber, companyName, licenseNumber, socialCreditCode, url);
                            NMPACollectionRecord record;
                            boolean isNew = false;
                            if (store.isExist(companyName)) {
                                record = store.get(companyName);
                            } else {
                                record = new NMPACollectionRecord();
                                isNew = true;
                            }
                            record.setCompanyName(companyName);
                            record.setLicenseNumber(licenseNumber);
                            record.setSocialCreditCode(socialCreditCode);
                            record.setUrl(url);
                            store.addOrUpdate(record);
                            log.info("{}第{}页,{},{}成功", logMsg, i, companyName, isNew ? "新增记录" : "修改记录");
                        } catch (Exception exception) {
                            log.info("{}第{}项,失败,错误消息:{}", logMsg, i, exception.getMessage());
                        }
                        ScrapeHelper.switchFirstTab(webDriver, true);
                        // log.warn("关闭第{}项详情页,切回列表页面", j + 1);
                    }
                    successCount[0] += 1;
                    ScrapeHelper.clickElement(webDriver, "html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(3) > div > div > button:nth-of-type(2)");
                    String finalUpCompanyName = upCompanyName;
                    ScrapeHelper.loopExecute(() -> {
                        // 判断翻页是否成功
                        WebElement webElement = ScrapeHelper.waitVisible(webDriver, "html > body > div:nth-of-type(1) > div:nth-of-type(3) > div:nth-of-type(2) > div > div > div:nth-of-type(3) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > div");
                        String name = webElement.getText();
                        log.info("上一个名称:{},翻页后名称：{}", finalUpCompanyName, name);
                        return !Objects.equals(name, finalUpCompanyName);
                    }, Duration.ofMinutes(1).toSeconds());
                    // log.warn("点击下一页按钮,跳转第{}页", i + 1);

                }
                return true;
            });
            log.info("[{}],[{}~{}],执行完成", desc, start, end);
        });
    }

    private void goToListPage(WebDriver webDriver, String desc) {
        closeMask(webDriver, desc);
        log.info("{},已关闭遮罩层", desc);
        String selector = "html > body > div > main > div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(5) > a";
        ScrapeHelper.loopExecute(() -> ScrapeHelper.clickElement(webDriver, selector), Duration.ofMinutes(1).toSeconds());
        log.info("{},导航面板已加载完成,点击药品生产企业选项链接", desc);
        WebElement input = ScrapeHelper.waitVisible(webDriver, "input[data-step='4']");
        input.sendKeys("公司");
        log.info("{},输入文本", desc);
        ScrapeHelper.clickElement(webDriver, "button[data-step='5']");
        log.info("{},点击搜索按钮", desc);
        ScrapeHelper.switchLastTab(webDriver, false);
        log.info("{},已切换标签", desc);
        closeMask(webDriver, desc);
        log.info("{},已关闭遮罩层", desc);
        ScrapeHelper.switchLastTab(webDriver, true);
        log.info("{},已关闭标签", desc);
    }

    private void closeMask(WebDriver webDriver, String desc) {
        ScrapeHelper.loopExecute(() -> {
            log.info("{},开始等待遮罩层出现", desc);
            ScrapeHelper.waitVisible(webDriver, "div[class='introjs-overlay']");
            log.info("{},点击遮罩层出现", desc);
            // 获取窗口的大小
            Dimension windowSize = webDriver.manage().window().getSize();
            int width = windowSize.getWidth();
            int height = windowSize.getHeight();
            // 计算窗口中间的坐标
            int middleX = width / 2;
            int middleY = height / 2;
            // 创建Actions对象
            Actions actions = new Actions(webDriver);
            // 移动到窗口中间并点击
            actions.moveByOffset(middleX, middleY).click().perform();
            log.info("{},点击遮罩层进行关闭", desc);
            return new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[class='introjs-overlay']")));
        }, Duration.ofMinutes(1).toSeconds());
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
