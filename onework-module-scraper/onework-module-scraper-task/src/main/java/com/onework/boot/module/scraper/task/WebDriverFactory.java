package com.onework.boot.module.scraper.task;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class WebDriverFactory {

    public final static List<WebDriver> drivers = new ArrayList<>();

    /**
     *
     * @param function 获取 WebDriver 方法
     * @param configuration 配置信息
     * @return WebDriver 浏览器去掉
     * @param <T> 浏览器批准
     */
    public static <T> WebDriver getWebDriver(Function<T, WebDriver> function, T configuration) {
        WebDriver webDriver = function.apply(configuration);
        drivers.add(webDriver);
        return webDriver;
    }

    public static WebDriver getWebDriver(WebDriverConfiguration configuration) {
        WebDriver webDriver = null;
        switch (configuration.getDriverType()) {
            case FIREFOX: {
                webDriver = new FirefoxDriver(getFirefoxOptions(configuration));
                break;
            }
            case CHROME: {
                webDriver = new ChromeDriver(getChromeOptions(configuration));
                break;
            }
            case IE: {
                webDriver = new InternetExplorerDriver(getInternetExplorerOptions(configuration));
                break;
            }
            case EDGE: {
                webDriver = new EdgeDriver(getEdgeOptions(configuration));
                break;
            }
        }
        drivers.add(webDriver);
        return webDriver;
    }

    private static FirefoxOptions getFirefoxOptions(WebDriverConfiguration configuration) {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("general.useragent.override", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        if (!configuration.isOpenWindow()) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static ChromeOptions getChromeOptions(WebDriverConfiguration configuration) {
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", Collections.singletonList("false"));
        options.addArguments("--disable-blink-features");
        options.addArguments("--disable-blink-features=AutomationControlled");

        if (configuration.isIncognito()) {
            options.addArguments("--incognito"); // 启用无痕模式
        }
        if (!configuration.isOpenWindow()) {
            options.addArguments("--headless");
        }
        return options;
    }

    private static InternetExplorerOptions getInternetExplorerOptions(WebDriverConfiguration configuration) {
        InternetExplorerOptions options = new InternetExplorerOptions();
        if (configuration.isIncognito()) {
            options.addCommandSwitches("-private"); // 启用无痕模式
        }
        return options;
    }

    private static EdgeOptions getEdgeOptions(WebDriverConfiguration configuration) {
        EdgeOptions options = new EdgeOptions();
        if (configuration.isIncognito()) {
            options.addArguments("--inprivate"); // 启用无痕模式
        }
        if (!configuration.isOpenWindow()) {
            options.addArguments("-headless");
        }
        return options;
    }
}
