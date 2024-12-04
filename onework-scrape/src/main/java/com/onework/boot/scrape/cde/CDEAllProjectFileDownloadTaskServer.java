package com.onework.boot.scrape.cde;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.cde.threads.FileDownloadThread;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.ScrapeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  下载所有项目文件
 */
@Component
public class CDEAllProjectFileDownloadTaskServer implements ITaskServer {

    private final CDEProjectRecordStore CDEProjectRecordStore;

    private final ScrapeConfiguration scrapeConfiguration;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CDEAllProjectFileDownloadTaskServer(ScrapeConfiguration scrapeConfiguration, CDEProjectRecordStore CDEProjectRecordStore) {

        this.CDEProjectRecordStore = CDEProjectRecordStore;
        this.scrapeConfiguration = scrapeConfiguration;
    }

    @Override
    public void run() {
        LOG.info("启动采集所有CDE项目服务（AllProjectProcessServer）");

        int numThreads = scrapeConfiguration.getThreadCount();
        int totalData = CDEWebDriverHelper.getProjectTotal(scrapeConfiguration);
        LOG.info("启动采集所有CDE项目服务（AllProjectProcessServer），共有{}项", totalData);
        // 初始化登记号数据，过滤已处理登记号
        CDEProjectRecordStore.initData();
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
            executor.execute(new FileDownloadThread(scrapeConfiguration, CDEProjectRecordStore, start, end));
            // 更新下一个线程的起始条数
            start = end + 1;
        }
        executor.shutdown();
    }
}
