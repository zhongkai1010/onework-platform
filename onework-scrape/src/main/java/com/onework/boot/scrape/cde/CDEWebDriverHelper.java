package com.onework.boot.scrape.cde;

import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class CDEWebDriverHelper {

    /**
     * 创建 ChromeDriver 对象，打开链接，获取项目总数
     */
    public static int getProjectTotal(ScrapeConfiguration scrapeConfiguration) {

        WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
        webDriver.get(scrapeConfiguration.getCdeCollectionUrl());
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                WebElement pageInfoElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("pageInfo")));
                String text = pageInfoElement.findElements(By.tagName("i")).get(2).getText();
                webDriver.quit();
                return Integer.parseInt(text);
            } catch (Exception exception) {
                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
            }
        }
    }

    /**
     * 获取当前页面分页总数
     * @param webDriver 浏览器驱动
     * @return 分页总数
     */
    public static int getProjectPageTotal(WebDriver webDriver) {
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                WebElement pageInfoElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/div[2]/div/div/div/div/div[6]/div/i[2]")));
                return Integer.parseInt(pageInfoElement.getText());
            } catch (Exception exception) {
                // 如果没有找到元素，刷新页面
                webDriver.navigate().refresh();
            }
        }
    }

    /**
     * 点击表格第一行链接，跳转项目详情页面
     * @param webDriver 浏览器驱动
     */
    public static void goToDetails(WebDriver webDriver) {
        while (true) {
            try {
                // 点击表格第一行链接，跳转项目详情页面
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                WebElement linkElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/div[2]/div/div/div/div/div[5]/table/tbody/tr[2]/td[2]/a")));
                linkElement.click();
                // 切换到新打开的页面
                Object[] windowHandles = webDriver.getWindowHandles().toArray();
                webDriver.switchTo().window((String) windowHandles[1]);
                break;
            } catch (Exception exception) {
                // 如果没有找到元素，刷新页面
                webDriver.navigate().refresh();
            }
        }
    }

    /**
     * 执行JavaScript脚本，跳转指定页码页码
     * @param webDriver 浏览器驱动
     * @param page 页码
     */
    public static void goToPage(WebDriver webDriver, int page) {
        while (true) {
            try {
                // 点击表格第一行链接，跳转项目详情页面
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/div[2]/div/div/div/div/div[8]/div/a")));
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript("gotopage(" + page + ")");
                break;
            } catch (Exception exception) {
                // 如果没有找到元素，刷新页面
                webDriver.navigate().refresh();
            }
        }
    }

    /**
     * 获取当前页面登记号
     *
     * @param webDriver 浏览器驱动
     * @return 登记号
     */
    public static String getRegistrationNo(WebDriver webDriver) {
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                // 尝试寻找元素，最多等待5秒
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/main/div[2]/div/div/div/div/div[5]/div/div[1]/div[2]/div/table/tbody/tr[1]/td[1]")));
                return element.getText();
            } catch (Exception exception) {

                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
//                LOG.warn("获取当前页面登记号(getRegistrationNo)异常，刷新页面，错误消息：{}", exception.getMessage());
            }
        }
    }

    /**
     * 搜索登记号，查询结果，跳转详情页面
     * @param webDriver 浏览器驱动
     * @param registrationNo 登记号
     */
    public static void searchRegistrationNo(WebDriver webDriver, String registrationNo) {
        while (true) {
            try {
                // 输入登记号
                WebDriverWait inputWait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                WebElement inputElement = inputWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[class$='subSearchInput']")));
                inputElement.clear();
                inputElement.sendKeys(registrationNo);
                /// 点击搜索
                WebElement buttonElement = webDriver.findElement(By.cssSelector("button[onclick='searchList()']"));
                buttonElement.click();
                break;
            } catch (Exception exception) {
                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
//                LOG.warn("获取当前页面登记号(getRegistrationNo)异常，刷新页面，错误消息：{}", exception.getMessage());
            }
        }
    }

    /**
     *  返回列表页面
     * @param webDriver 浏览器驱动
     */
    public static void backListPage(WebDriver webDriver) {
        // 获取所有窗口句柄
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        // 关闭当前标签页
        webDriver.close();
        // 切换回第一个标签页
        webDriver.switchTo().window(tabs.get(0));
    }
}
