package com.onework.collection.cde;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class OneworkScraperCDEServerApplication implements CommandLineRunner {

    private final ServerConfiguration serverConfiguration;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScraperCDEServerApplication.class);

    public OneworkScraperCDEServerApplication(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

    public static void main(String[] args) {
        SpringApplication.run(OneworkScraperCDEServerApplication.class, args);
    }

    @Override
    public void run(String... args) {

        int numThreads = serverConfiguration.getThreadCount();
        int totalData = ServiceHelper.getProjectTotal(serverConfiguration);

        // 计算每个线程处理的基本条数
        int baseCount = totalData / numThreads;
        // 计算剩余条数，分配给部分线程
        int remainder = totalData % numThreads;

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // 创建线程并分配工作
        int start = 1;  // 从第1条数据开始
        for (int i = 0; i < numThreads; i++) {
            // 计算每个线程需要处理的数据条数
            int end = start + baseCount + (i < remainder ? 1 : 0) - 1;
            executor.execute(new CollectionThread(serverConfiguration,start, end));
            // 更新下一个线程的起始条数
            start = end + 1;
        }
        executor.shutdown();
    }
}
