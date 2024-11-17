package com.onework.boot.ctr.collection.process;

import com.onework.boot.ctr.collection.OneworkCTRCollectionApplication;
import com.onework.boot.ctr.collection.RegistrationNumberStore;
import com.onework.boot.ctr.collection.ServerConfiguration;
import com.onework.boot.ctr.collection.ServiceHelper;
import com.onework.boot.ctr.collection.thread.CollectionListThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScanListProcessServer implements IProcessServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCTRCollectionApplication.class);
    private final RegistrationNumberStore registrationNumberStore;
    private final ServerConfiguration serverConfiguration;

    public ScanListProcessServer(RegistrationNumberStore registrationNumberStore, ServerConfiguration serverConfiguration) {
        this.registrationNumberStore = registrationNumberStore;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void run() {
        registrationNumberStore.initData();
        LOG.info("启动检索项目列表，记录项目数据服务（AllProjectProcessServer）");
        int totalDataCount = ServiceHelper.getTotalPage(serverConfiguration);
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
            executor.execute(new CollectionListThread(serverConfiguration, registrationNumberStore, start, end));
            start = end + 1;
        }
        executor.shutdown();
        LOG.info("检索项目列表，记录项目数据服务（AllProjectProcessServer），执行完成，退出服务");

    }
}
