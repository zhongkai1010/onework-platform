package com.onework.boot.ctr.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
public class OneworkCTRCollectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(OneworkCTRCollectionApplication.class, args);
    }
}