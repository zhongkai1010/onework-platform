package com.onework.boot.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OneWork Server Application
 *
 * @author onework
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${onework.info.base-package}
@SpringBootApplication(scanBasePackages = {"${onework.info.base-package}.server", "${onework.info.base-package}.module"})
public class OneWorkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneWorkServerApplication.class, args);
    }
} 