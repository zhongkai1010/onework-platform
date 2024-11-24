package com.onework.boot.ctr.collection.tasks;

import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.ProjectRecordStore;
import com.onework.boot.ctr.collection.ServerConfiguration;
import com.onework.boot.ctr.collection.threads.FileDownloadThread;
import com.onework.boot.data.entity.CTRCollectionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FileDownloadTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    private final ServerConfiguration serverConfiguration;

    private final ProjectRecordStore projectRecordStore;

    public FileDownloadTaskServer(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;

    }

    @Override
    public void run() {
        LOG.info("启动CTR项目文件下载服务（FileDownloadProcessServer）");
        List<CTRCollectionRecord> records = projectRecordStore.getNotDownloadProjects();
        int totalDataCount = records.size();
        int numThreads = serverConfiguration.getThreadCount();
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
            executor.execute(new FileDownloadThread(pageData, serverConfiguration, projectRecordStore, startItem, endItem + 1));
        }
        executor.shutdown();
        LOG.info("CTR项目文件下载服务（FileDownloadProcessServer），执行完成");

    }
}
