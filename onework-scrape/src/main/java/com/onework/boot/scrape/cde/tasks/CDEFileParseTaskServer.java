package com.onework.boot.scrape.cde.tasks;

import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.cde.store.CDEProjectRecordStore;
import com.onework.boot.scrape.cde.threads.FileParseThread;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.ServerConfiguration;
import com.onework.boot.scrape.data.entity.CDECollectionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 解析已下载项目文件服务
 */
@Component
public class CDEFileParseTaskServer implements ITaskServer {

    private final ServerConfiguration serverConfiguration;

    private final CDEProjectRecordStore CDEProjectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CDEFileParseTaskServer(ServerConfiguration serverConfiguration, CDEProjectRecordStore CDEProjectRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.CDEProjectRecordStore = CDEProjectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("启动项目文件解析服务（FileParseProcessServer）");

        List<CDECollectionRecord> records = CDEProjectRecordStore.getNotParseProjects();

        long totalData = records.size();
        LOG.info("项目文件解析服务（FileParseProcessServer），共{}条数据处理", totalData);

        int numThreads = serverConfiguration.getThreadCount();
        // 基础分配数量
        int dataPerPage = Math.toIntExact(totalData / numThreads);
        // 余数（需要多处理的项数）
        int remainder = Math.toIntExact(totalData % numThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        // 输出每个线程的任务量
        for (int i = 0; i < numThreads; i++) {
            int startItem = i * dataPerPage + (Math.min(i, remainder));  // 起始项
            int endItem = startItem + dataPerPage + (i < remainder ? 1 : 0) - 1;  // 结束项
            // 确保索引有效（避免越界）
            startItem = Math.max(0, startItem);
            endItem = Math.min(records.size() - 1, endItem);
            List<CDECollectionRecord> pageData = records.subList(startItem, endItem + 1);  // 获取数据区间
            executor.execute(new FileParseThread(startItem, endItem + 1, pageData, CDEProjectRecordStore));
        }
        executor.shutdown();
    }
}
