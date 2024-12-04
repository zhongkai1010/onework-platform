package com.onework.boot.scrape.ctr;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.data.entity.CTRCollectionRecord;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CTRWebDriverHelper {

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    private static LocalDateTime _getTryLocalDateTime(String value) {
        try {
            return LocalDateTimeUtil.parse(value, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 解析当前列表页面，返回记录集合
     * @param webDriver 浏览器驱动
     * @return 记录集合
     */
    public static List<CTRCollectionRecord> getTableContext(WebDriver webDriver) {
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); // 设置等待时间
                WebElement table1WebElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By.className("table1"))));
                List<WebElement> trsWebElement = table1WebElement.findElements(By.tagName("tr"));
                List<CTRCollectionRecord> records = new ArrayList<>();

                for (int i = 0; i < trsWebElement.size(); i++) {
                    if (i == 0) {
                        continue;
                    }
                    CTRCollectionRecord record = new CTRCollectionRecord();
                    WebElement trWebElement = trsWebElement.get(i);
                    List<WebElement> tdsWebElement = trWebElement.findElements(By.tagName("td"));

                    String historyVersionLink = tdsWebElement.get(0).findElement(By.tagName("a")).getAttribute("href");
                    record.setHistoryVersionLink(historyVersionLink);

                    String registrationNumber = tdsWebElement.get(1).getText();
                    record.setRegistrationNumber(registrationNumber);

                    WebElement aWebElement = tdsWebElement.get(2).findElement(By.tagName("a"));
                    String title = aWebElement.getAttribute("title");
                    record.setRegistrationTitle(title);

                    String hospital = trWebElement.findElement(By.tagName("p")).getText();
                    record.setApplicantInstitution(hospital);

                    String type = tdsWebElement.get(3).getText();
                    record.setStudyType(type);

                    String date = tdsWebElement.get(4).getText();
                    record.setRegistrationTime(_getTryLocalDateTime(date));

                    String url = aWebElement.getAttribute("href");
                    record.setProjectLink(url);

                    records.add(record);
                }
                return records;
            } catch (Exception exception) {
                ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(tabs.get(tabs.size() - 1));
            }
        }
    }

    public static void nextPage(WebDriver webDriver) {
        try {
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); // 设置等待时间
            WebElement paginationWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("pagination"))));
            List<WebElement> liWebElements = paginationWebElement.findElements(By.tagName("li"));
            for (WebElement liWebElement : liWebElements) {
                String textWebElement = liWebElement.getText();
                if (textWebElement.equals(">")) {
                    liWebElement.click();
                }
            }
        } catch (Exception exception) {
            ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
            webDriver.switchTo().window(tabs.get(tabs.size() - 1));
        }
    }

    /**
     * 获取CTR网站总分页数
     * @param scrapeConfiguration 服务配置
     * @return 总分页数
     */
    public static int getTotalPage(ScrapeConfiguration scrapeConfiguration) {
        WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
        while (true) {
            try {
                webDriver.get(scrapeConfiguration.getCtrCollectionUrl());
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); // 设置等待时间
                WebElement paginationWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("pagination"))));
                WebElement totalPageWebElement = paginationWebElement.findElements(By.className("totalPage")).get(0);
                String totalPageText = totalPageWebElement.getText().replaceAll("\\D", "");
                webDriver.quit();
                return Integer.parseInt(totalPageText);
            } catch (Exception e) {
                ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(tabs.get(tabs.size() - 1));
            }
        }
    }

    public static String saveProject(WebDriver webDriver, ScrapeConfiguration scrapeConfiguration) {
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); // 设置等待时间
                wait.until(ExpectedConditions.visibilityOfElementLocated((By.className("retrieve-box"))));
                String html = webDriver.getPageSource();

                Pattern scriptPattern = Pattern.compile("<script.*?>.*?</script>", Pattern.DOTALL);
                Matcher matcher = scriptPattern.matcher(html);
                // 替换<script>标签内容为空字符串
                String newHtmlContent = matcher.replaceAll("");

                WebElement registrationNumberWebElement = webDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[3]/table/tbody/tr[1]/td[2]"));
                String registrationNumber = registrationNumberWebElement.getText();
                String filePathName = String.format("%s\\%s.html", scrapeConfiguration.getCtrSavePath(), registrationNumber.trim());
                FileUtil.writeString(newHtmlContent, filePathName, StandardCharsets.UTF_8);
                return filePathName;

            } catch (Exception exception) {
                ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
                webDriver.switchTo().window(tabs.get(tabs.size() - 1));
            }
        }
    }
}
