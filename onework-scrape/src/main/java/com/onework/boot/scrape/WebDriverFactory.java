package com.onework.boot.scrape;

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

import java.util.Collections;

@Slf4j
@Component
public class WebDriverFactory {

    public static WebDriver getWebDriver(WebDriverConfiguration configuration) {

        return switch (configuration.getDriverType()) {
            case FIREFOX -> new FirefoxDriver(getFirefoxOptions(configuration));
            case CHROME -> new ChromeDriver(getChromeOptions(configuration));
            case IE -> new InternetExplorerDriver(getInternetExplorerOptions(configuration));
            case EDGE -> new EdgeDriver(getEdgeOptions(configuration));
        };
    }

    private static FirefoxOptions getFirefoxOptions(WebDriverConfiguration configuration) {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette",false);
        if (configuration.isIncognito()) {
            options.addArguments("-private"); // 启用无痕模式
        }
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
