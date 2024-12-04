package com.onework.boot.scrape.ctr;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ctr.store.CTRProjectRecordStore;
import com.onework.boot.scrape.ctr.threads.FileDownloadThread;
import com.onework.boot.scrape.data.entity.CTRCollectionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CTRFileDownloadTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final ScrapeConfiguration scrapeConfiguration;

    private final CTRProjectRecordStore ctrProjectRecordStore;

    public CTRFileDownloadTaskServer(ScrapeConfiguration scrapeConfiguration, CTRProjectRecordStore ctrProjectRecordStore) {
        this.scrapeConfiguration = scrapeConfiguration;
        this.ctrProjectRecordStore = ctrProjectRecordStore;
        this.ctrProjectRecordStore.initData();
    }

    @Override
    public void run() {

        LOG.info("启动CTR项目文件下载服务（FileDownloadProcessServer）");
        List<CTRCollectionRecord> records = ctrProjectRecordStore.getNotDownloadProjects();
        int totalDataCount = records.size();
        int numThreads = scrapeConfiguration.getThreadCount();
        LOG.info("启动CTR项目文件下载服务（FileDownloadProcessServer），共计{}项，开启{}线程处理", totalDataCount, numThreads);
        // 基础分配数量
        int dataPerPage = Math.toIntExact(totalDataCount / numThreads);
        // 余数（需要多处理的项数）
        int remainder = Math.toIntExact(totalDataCount % numThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        // 输出每个线程的任务量
        for (int i = 0; i < numThreads; i++) {
            int startItem = i * dataPerPage + (Math.min(i, remainder));  // 起始项
            int endItem = startItem + dataPerPage + (i < remainder ? 1 : 0) - 1;  // 结束项
            // 确保索引有效（避免越界）
            startItem = Math.max(0, startItem);
            endItem = Math.min(records.size() - 1, endItem);
            List<CTRCollectionRecord> pageData = records.subList(startItem, endItem + 1);  // 获取数据区间
            LOG.info("启动CTR项目文件下载服务（FileDownloadProcessServer），[线程（{}-{}）],开始处理", startItem, endItem + 1);
            executor.execute(new FileDownloadThread(pageData, scrapeConfiguration, ctrProjectRecordStore, startItem, endItem + 1));
        }
        executor.shutdown();
        LOG.info("CTR项目文件下载服务（FileDownloadProcessServer），执行完成");

    }
}
