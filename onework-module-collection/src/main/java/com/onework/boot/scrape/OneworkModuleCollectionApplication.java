package com.onework.boot.scrape;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
@Slf4j
public class OneworkModuleCollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneworkModuleCollectionApplication.class, args);
    }
}


