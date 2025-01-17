package com.onework.boot.task.collection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
@Slf4j
public class OneworkTaskCollectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneworkTaskCollectionApplication.class, args);
    }

}
