package com.onework.boot.scrape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
public class OneworkScrapeApplication   {

    public static void main(String[] args) {
        SpringApplication.run(OneworkScrapeApplication.class, args);
    }
}
