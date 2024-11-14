package com.onework.collection.cde;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;

public class ServiceHelper {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScraperCDEServerApplication.class);

    /**
     * 配置 ChromeOptions 默认参数，获取 ChromeDriver 对象 ，默认有界面操作
     * @return ChromeDriver 对象
     */
    public static ChromeDriver getWebDriver() {
        return getWebDriver(true);
    }

    /**
     * 配置 ChromeOptions 默认参数，获取 ChromeDriver 对象
     * @param window 是否无界面模式 true:开启界面，false：无界面
     * @return ChromeDriver 对象
     */
    public static ChromeDriver getWebDriver(boolean window) {

        System.setProperty("webdriver.chrome.driver", "D:\\tools\\chromedriver-win64\\chromedriver.exe");
        // 禁用 Selenium 的日志输出
        System.setProperty("webdriver.chrome.silentOutput", "true");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-logging"));
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");

        if (!window) {
            chromeOptions.addArguments("--headless"); // # 开启无界面模式
        }

        return new ChromeDriver(chromeOptions);
    }

    /**
     * 创建 ChromeDriver 对象，打开链接，获取项目总数
     * @param configuration 服务配置
     * @return 项目总数
     */
    public static int getProjectTotal(ServerConfiguration configuration) {
        System.setProperty("webdriver.chrome.driver", configuration.getDrivePath());
        WebDriver webDriver = ServiceHelper.getWebDriver(false);
        webDriver.get(configuration.getCollectionUrl());
        while (true) {
            try {
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5)); // 设置等待时间为5秒
                WebElement pageInfoElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("pageInfo")));
                String text = pageInfoElement.findElements(By.tagName("i")).get(2).getText();
                webDriver.quit();
                return Integer.parseInt(text);
            } catch (Exception exception) {
                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
                LOG.warn("获取页面项目总数(getProjectTotal)异常，刷新页面，错误消息：{}", exception.getMessage());
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
                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
                LOG.warn("点击表格第一行链接，跳转项目详情页面(goToDetails)异常，刷新页面，错误消息：{}", exception.getMessage());
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
                JavascriptExecutor executor = (JavascriptExecutor) webDriver;
                executor.executeScript("gotopage(" + page + ")");
                break;
            } catch (Exception exception) {
                webDriver.navigate().refresh(); // 如果没有找到元素，刷新页面
                LOG.warn("执行JavaScript脚本，跳转指定页码页码(goToPage)异常，刷新页面，错误消息：{}", exception.getMessage());
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
                LOG.warn("获取当前页面登记号(getRegistrationNo)异常，刷新页面，错误消息：{}", exception.getMessage());
            }
        }
    }
}
