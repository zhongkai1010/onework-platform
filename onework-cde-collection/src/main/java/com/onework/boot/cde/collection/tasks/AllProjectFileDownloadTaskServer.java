package com.onework.boot.cde.collection.tasks;

import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.ProjectRecordStore;
import com.onework.boot.cde.collection.ServerConfiguration;
import com.onework.boot.cde.collection.WebDriverHelper;
import com.onework.boot.cde.collection.threads.FileDownloadThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  下载所有项目文件
 */
@Component
public class AllProjectFileDownloadTaskServer implements ITaskServer {

    private final ServerConfiguration serverConfiguration;

    private final ProjectRecordStore projectRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    public AllProjectFileDownloadTaskServer(ServerConfiguration serverConfiguration, ProjectRecordStore projectRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.projectRecordStore = projectRecordStore;
    }

    @Override
    public void run() {
        LOG.info("启动采集所有CDE项目服务（AllProjectProcessServer）");

        int numThreads = serverConfiguration.getThreadCount();
        int totalData = WebDriverHelper.getProjectTotal(serverConfiguration);
        LOG.info("启动采集所有CDE项目服务（AllProjectProcessServer），共有{}项", totalData);
        // 初始化登记号数据，过滤已处理登记号
        projectRecordStore.initData();
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
            executor.execute(new FileDownloadThread(serverConfiguration, projectRecordStore, start, end));
            // 更新下一个线程的起始条数
            start = end + 1;
        }
        executor.shutdown();
    }
}
