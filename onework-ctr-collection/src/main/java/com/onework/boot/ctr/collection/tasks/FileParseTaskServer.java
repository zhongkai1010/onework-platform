package com.onework.boot.ctr.collection.tasks;

import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.ProjectRecordStore;
import com.onework.boot.ctr.collection.ServerConfiguration;
import com.onework.boot.ctr.collection.threads.FileParseThread;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.entity.CTRCollectionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FileParseTaskServer implements ITaskServer {


    private final ServerConfiguration serverConfiguration;

    private ProjectRecordStore projectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);

    public FileParseTaskServer(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("启动项目文件解析服务（FileParseProcessServer）");

        List<CTRCollectionRecord> records = projectRecordStore.getNotParseProjects();

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
            List<CTRCollectionRecord> pageData = records.subList(startItem, endItem + 1);  // 获取数据区间
            executor.execute(new FileParseThread(startItem, endItem + 1, pageData, projectRecordStore));
        }
        executor.shutdown();
    }
}
