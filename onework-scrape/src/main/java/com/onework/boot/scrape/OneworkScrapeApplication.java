package com.onework.boot.scrape;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
public class OneworkScrapeApplication implements CommandLineRunner {

    @Autowired
    private ScrapeConfiguration scrapeConfiguration;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OneworkScrapeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WebDriver webDriver = ScrapeHelper.getWebDriver(scrapeConfiguration);
        webDriver.get("https://www.nmpa.gov.cn/datasearch/home-index.html");
        WebElement webElement = ScrapeHelper.find(webDriver, "html > body > div > main > div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(1) > div:nth-of-type(1) > div:nth-of-type(5) > a");
        System.out.println(webElement.getAttribute("innerHTML"));
        webDriver.quit();
    }
}


