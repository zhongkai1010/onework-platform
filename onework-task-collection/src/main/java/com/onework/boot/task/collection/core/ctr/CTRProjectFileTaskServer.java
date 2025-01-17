package com.onework.boot.task.collection.core.ctr;

import com.onework.boot.task.collection.core.ScrapeHelper;
import com.onework.boot.task.collection.core.TaskServer;
import com.onework.boot.task.collection.core.TaskServerType;
import com.onework.boot.task.collection.core.WebDriverFactory;
import com.onework.boot.task.collection.core.ctr.config.CTRProjectFileConfiguration;
import com.onework.boot.task.collection.core.ctr.store.CTRProjectRecordStore;
import com.onework.boot.task.collection.dao.entity.CTRCollectionRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CTRProjectFileTaskServer extends TaskServer {

    private final CTRProjectFileConfiguration configuration;

    private final CTRProjectRecordStore ctrProjectRecordStore;

    public CTRProjectFileTaskServer(CTRProjectFileConfiguration configuration, CTRProjectRecordStore ctrProjectRecordStore) {
        this.configuration = configuration;
        this.ctrProjectRecordStore = ctrProjectRecordStore;
    }

    @Override
    protected void beforeStart() {
        ctrProjectRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CTR_PROJECT_FILE;
    }

    @Override
    public void run() {
        List<CTRCollectionRecord> records = ctrProjectRecordStore.getNotDownloadProjects();
        String desc = getTaskServerType().getDescription();
        log.info("[{}],共有{}页", desc, records.size());

        ScrapeHelper.listWorkerExecute(records, configuration.getThreadCount(), (start, end, items) -> {
            final int[] successCount = {0};
            ScrapeHelper.continueExecute(() -> WebDriverFactory.getWebDriver(configuration), webDriver -> {
                for (int i = 0; i < items.size(); i++) {
                    // 防止意外退出，从异常开始
                    if (i != successCount[0]) {
                        continue;
                    }
                    CTRCollectionRecord record = items.get(i);
                    webDriver.get(record.getProjectLink());
                    String selector = "html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2)";
                    String registrationNumber = ScrapeHelper.getText(webDriver, selector).trim();
                    String filePathName = String.format("%s\\%s.html", configuration.getSavePath(), registrationNumber.trim());
                    ScrapeHelper.savePage(webDriver, filePathName);
                    try {
                        ctrProjectRecordStore.markDownload(registrationNumber, filePathName);
                        log.info("[{}],第{}项,登记号:{},文件成功", desc, start + i, registrationNumber);

                    } catch (Exception exception) {
                        log.info("[{}],第{}项,登记号:{},文件下载失败,错误消息:{}", desc, start + i, registrationNumber, exception.getMessage());
                    }
                    successCount[0] += 1;
                }
                return true;
            }, webDriver -> ScrapeHelper.switchLastTab(webDriver, false));
        });
    }
}
