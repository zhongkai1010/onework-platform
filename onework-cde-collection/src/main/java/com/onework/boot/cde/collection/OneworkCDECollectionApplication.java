package com.onework.boot.cde.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
public class OneworkCDECollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneworkCDECollectionApplication.class, args);
    }
}
