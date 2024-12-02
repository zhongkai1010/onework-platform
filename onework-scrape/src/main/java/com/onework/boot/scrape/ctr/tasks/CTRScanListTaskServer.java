package com.onework.boot.scrape.ctr.tasks;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.ServerConfiguration;
import com.onework.boot.scrape.ctr.CTRWebDriverHelper;
import com.onework.boot.scrape.ctr.store.CTRProjectRecordStore;
import com.onework.boot.scrape.ctr.threads.ScanListThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CTRScanListTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final CTRProjectRecordStore CTRProjectRecordStore;
    private final ServerConfiguration serverConfiguration;

    public CTRScanListTaskServer(CTRProjectRecordStore CTRProjectRecordStore, ServerConfiguration serverConfiguration) {
        this.CTRProjectRecordStore = CTRProjectRecordStore;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void run() {
        CTRProjectRecordStore.initData();
        LOG.info("启动检索项目列表，记录项目数据服务（AllProjectProcessServer）");
        int totalDataCount = CTRWebDriverHelper.getTotalPage(serverConfiguration);
        LOG.info("启动检索项目列表，记录项目数据服务（AllProjectProcessServer），共计{}条", totalDataCount);
        int threadCount = serverConfiguration.getThreadCount();
        // 计算每个线程应处理的最小数据量
        int dataPerThread = totalDataCount / threadCount;
        // 计算不能均匀分配的余数
        int remainder = totalDataCount % threadCount;
        int start = 1;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            int end = start + dataPerThread - 1;
            if (i < remainder) {
                end++;
            }
            executor.execute(new ScanListThread(serverConfiguration, CTRProjectRecordStore, start, end));
            start = end + 1;
        }
        executor.shutdown();
        LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer），执行完成，退出服务");

    }
}
