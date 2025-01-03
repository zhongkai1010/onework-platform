package com.onework.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
@Slf4j
public class OneworkScrapeApplication   {

    public static void main(String[] args) {
        SpringApplication.run(OneworkScrapeApplication.class, args);
    }
}


