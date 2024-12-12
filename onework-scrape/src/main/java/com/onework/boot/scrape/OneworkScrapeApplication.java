package com.onework.boot.scrape;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages = "${onework.info.base-package}")
@Slf4j
public class OneworkScrapeApplication implements CommandLineRunner {

    private final TaskServerFactory taskServerFactory;

    public OneworkScrapeApplication(TaskServerFactory taskServerFactory) {
        this.taskServerFactory = taskServerFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneworkScrapeApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 获取程序开始执行的时间
        long startTime = System.currentTimeMillis();

//        ITaskServer CTMDS_DRUG = taskServerFactory.getTaskServer(TaskServerType.CTMDS_DRUG);
//        CTMDS_DRUG.execute();
//
//        ITaskServer CTMDS_DRUG_FILE = taskServerFactory.getTaskServer(TaskServerType.CTMDS_DRUG_FILE);
//        CTMDS_DRUG_FILE.execute();
//
//        ITaskServer CTMDS_INSTRUMENT = taskServerFactory.getTaskServer(TaskServerType.CTMDS_INSTRUMENT);
//        CTMDS_INSTRUMENT.execute();
//
//        ITaskServer CTMDS_INSTRUMENT_FILE = taskServerFactory.getTaskServer(TaskServerType.CTMDS_INSTRUMENT_FILE);
//        CTMDS_INSTRUMENT_FILE.execute();
//
//        ITaskServer BOHE_COMPANY = taskServerFactory.getTaskServer(TaskServerType.CTR_PROJECT);
//        BOHE_COMPANY.execute();
//
//        ITaskServer CTR_PROJECT = taskServerFactory.getTaskServer(TaskServerType.CTR_PROJECT);
//        CTR_PROJECT.execute();
//
//        ITaskServer CTR_PROJECT_FILE = taskServerFactory.getTaskServer(TaskServerType.CTR_PROJECT_FILE);
//        CTR_PROJECT_FILE.execute();
//
//        ITaskServer CTR_PROJECT_FILE_PARSE = taskServerFactory.getTaskServer(TaskServerType.CTR_PROJECT_FILE_PARSE);
//        CTR_PROJECT_FILE_PARSE.execute();

        // 获取程序结束执行的时间
        long endTime = System.currentTimeMillis();
        // 计算执行时间
        long duration = endTime - startTime;
        log.info("所有任务执行完成,共耗时{}秒", duration / 1000);
    }

    @PreDestroy
    public void destroy() throws IOException {
        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
        Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
    }
}


