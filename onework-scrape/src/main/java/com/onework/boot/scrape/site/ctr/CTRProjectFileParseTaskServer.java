package com.onework.boot.scrape.site.ctr;

import com.onework.boot.scrape.dal.dataobject.CTRCollectionRecord;
import com.onework.boot.scrape.dal.dataobject.CTRProject;
import com.onework.boot.scrape.site.ScrapeHelper;
import com.onework.boot.scrape.site.TaskServer;
import com.onework.boot.scrape.site.TaskServerType;
import com.onework.boot.scrape.site.ctr.config.CTRProjectFileParseConfiguration;
import com.onework.boot.scrape.site.ctr.store.CTRProjectRecordStore;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class CTRProjectFileParseTaskServer extends TaskServer {

    private final CTRProjectFileParseConfiguration configuration;

    private final CTRProjectRecordStore ctrProjectRecordStore;


    public CTRProjectFileParseTaskServer(CTRProjectFileParseConfiguration configuration, CTRProjectRecordStore ctrProjectRecordStore) {
        this.configuration = configuration;
        this.ctrProjectRecordStore = ctrProjectRecordStore;
    }

    @Override
    protected void beforeStart() {
        ctrProjectRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CTR_PROJECT_FILE_PARSE;
    }

    @Override
    protected void run() {
        String desc = getTaskServerType().getDescription();
        List<CTRCollectionRecord> records = ctrProjectRecordStore.getNotParseProjects();
        log.info("[{}],共有{}条", desc, records.size());

        int threadCount = configuration.getThreadCount();
        ScrapeHelper.listWorkerExecute(records, threadCount, (start, end, items) -> {
            for (int i = 1; i <= items.size(); i++) {
                CTRCollectionRecord record = items.get(i - 1);
                File file = new File(record.getFilePath());
                if (!file.exists()) {
                    log.info("[{}],第{}条,{},不存在", desc, start + i, record.getFilePath());
                } else {
                    try {
                        Document document = Jsoup.parse(file);
                        CTRProject project = CTRParseHelper.createProject(document);
                        try {
                            ctrProjectRecordStore.markParse(record.getRegistrationNumber(),project);
                            log.info("[{}],第{}条,登记号:{},保存记录成功", desc, start + i, record.getRegistrationNumber());

                        } catch (Exception exception) {
                            log.info("[{}],第{}条,登记号:{},保存记录失败,错误消息:{}", desc, start + i, record.getRegistrationNumber(), exception.getMessage());
                        }
                    } catch (Exception exception) {
                        log.info("[{}],第{}条,登记号:{},解析失败,错误消息:{}", desc, start + i, record.getRegistrationNumber(), exception.getMessage());
                    }
                }
            }
        });
    }
}
