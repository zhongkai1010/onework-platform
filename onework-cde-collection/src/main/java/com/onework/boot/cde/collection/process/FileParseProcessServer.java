package com.onework.boot.cde.collection.process;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.ServerConfiguration;
import com.onework.boot.cde.collection.thread.FileParseThread;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import com.onework.boot.data.mapper.CDEProjectMapper;
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
public class FileParseProcessServer implements IProcessServer {

    private final CDECollectionRecordMapper recordMapper;

    private final ServerConfiguration serverConfiguration;

    private final CDEProjectMapper projectMapper;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    public FileParseProcessServer(ServerConfiguration serverConfiguration, CDECollectionRecordMapper recordMapper, CDEProjectMapper projectMapper) {
        this.recordMapper = recordMapper;
        this.serverConfiguration = serverConfiguration;
        this.projectMapper = projectMapper;
    }

    @Override
    public void run() {
        LOG.info("启动项目文件解析服务（FileParseProcessServer）");

        List<CDECollectionRecord> records = recordMapper.selectList(new LambdaQueryWrapper<CDECollectionRecord>()
                .eq(CDECollectionRecord::getParseHandle, false).isNotNull(CDECollectionRecord::getFilePath));
        long totalData = records.size();
        LOG.info("启动项目文件解析服务（FileParseProcessServer），共{}条数据处理", totalData);

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
            LOG.info("启动项目文件解析服务（FileParseProcessServer），[线程（{}-{}）],开始处理", startItem, endItem + 1);
            executor.execute(new FileParseThread(pageData, startItem, endItem + 1, projectMapper, recordMapper));
        }
        executor.shutdown();
    }
}
