package com.onework.boot.task.collection.core.cde;

import com.onework.boot.task.collection.core.ScrapeHelper;
import com.onework.boot.task.collection.core.TaskServer;
import com.onework.boot.task.collection.core.TaskServerType;
import com.onework.boot.task.collection.core.cde.config.CDEProjectFileParseConfiguration;
import com.onework.boot.task.collection.core.cde.store.CDEProjectRecordStore;
import com.onework.boot.task.collection.dao.entity.CDECollectionRecord;
import com.onework.boot.task.collection.dao.entity.CDEProject;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 解析已下载项目文件服务
 */
@Slf4j
@Component
public class CDEProjectFileParseTaskServer extends TaskServer {

    private final CDEProjectFileParseConfiguration configuration;

    private final CDEProjectRecordStore cdeProjectRecordStore;

    public CDEProjectFileParseTaskServer(CDEProjectFileParseConfiguration configuration, CDEProjectRecordStore cdeProjectRecordStore) {
        this.configuration = configuration;
        this.cdeProjectRecordStore = cdeProjectRecordStore;
    }

    @Override
    protected void beforeStart() {
        cdeProjectRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CDE_PROJECT_FILE_PARSE;
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();
        List<CDECollectionRecord> records = cdeProjectRecordStore.getNotParseProjects();
        log.info("[{}],共有{}条", desc, records.size());

        int threadCount = configuration.getThreadCount();
        ScrapeHelper.listWorkerExecute(records, threadCount, (start, end, items) -> {
            for (int i = 1; i <= items.size(); i++) {
                CDECollectionRecord record = items.get(i - 1);
                File file = new File(record.getFilePath());
                if (!file.exists()) {
                    log.info("[{}],第{}条,{},不存在", desc, start + i, record.getFilePath());
                    continue;
                }
                try {
                    Document document = Jsoup.parse(file);
                    CDEProject project = CDEParseHelper.createProject(document);
                    try {
                        cdeProjectRecordStore.markParse(record.getRegistrationNumber(), project);
                        log.info("[{}],第{}条,登记号:{},保存记录成功", desc, start + i, record.getRegistrationNumber());

                    } catch (Exception exception) {
                        log.info("[{}],第{}条,登记号:{},保存记录失败,错误消息:{}", desc, start + i, record.getRegistrationNumber(), exception.getMessage());
                    }
                } catch (Exception exception) {
                    log.info("[{}],第{}条,登记号:{},解析失败,错误消息:{}", desc, start + i, record.getRegistrationNumber(), exception.getMessage());
                }
            }
        });
    }
}
