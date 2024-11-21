package com.onework.boot.cde.collection.tasks;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.onework.boot.cde.collection.OneworkCDECollectionApplication;
import com.onework.boot.cde.collection.ServerConfiguration;
import com.onework.boot.cde.collection.WebDriverHelper;
import com.onework.boot.data.entity.CDECollectionRecord;
import com.onework.boot.data.mapper.CDECollectionRecordMapper;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *  检索未下载文件记录，进行文件下载
 */
@Component
public class ProjectFileDownloadTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkCDECollectionApplication.class);

    private final CDECollectionRecordMapper recordMapper;

    private final ServerConfiguration serverConfiguration;

    public ProjectFileDownloadTaskServer(CDECollectionRecordMapper recordMapper, ServerConfiguration serverConfiguration) {
        this.recordMapper = recordMapper;
        this.serverConfiguration = serverConfiguration;
    }

    @Override
    public void run() {
        LOG.info("启动下载项目文件服务（FileDownloadProcessServer）");
        List<CDECollectionRecord> records = recordMapper.selectList(Wrappers.<CDECollectionRecord>lambdaQuery().isNull(CDECollectionRecord::getFilePath));
        if (records.isEmpty()) {
            LOG.info("下载项目文件服务（FileDownloadProcessServer），无最新记录，退出服务");
        } else {
            LOG.info("下载项目文件服务（FileDownloadProcessServer），共{}条，开始处理", records.size());
            while (true) {
                WebDriver webDriver = WebDriverHelper.getWebDriver(serverConfiguration);
                try {
                    webDriver.get(serverConfiguration.getCollectionUrl());
                    LOG.info("下载项目文件服务（FileDownloadProcessServer），共{}条，成功打开链接", records.size());
                    for (CDECollectionRecord record : records) {
                        WebDriverHelper.searchRegistrationNo(webDriver, record.getRegistrationNumber());
                        WebDriverHelper.goToDetails(webDriver);
                        String registrationNo = WebDriverHelper.getRegistrationNo(webDriver);
                        String html = webDriver.getPageSource();
                        String filePathName = String.format("%s\\%s.html", serverConfiguration.getSavePath(), registrationNo);
                        FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
                        record.setFilePath(filePathName);
                        recordMapper.updateById(record);
                        WebDriverHelper.backListPage(webDriver);
                        LOG.info("下载项目文件服务（FileDownloadProcessServer），{}，下载成功", record.getFilePath());
                    }
                    webDriver.quit();
                    break;
                } catch (Exception exception) {
                    webDriver.quit();
                    LOG.info("下载项目文件服务（FileDownloadProcessServer），出现异常，错误消息：{}", exception.getMessage());
                } finally {
                    webDriver.quit();
                }
            }
        }
        LOG.info("下载项目文件服务（FileDownloadProcessServer），执行完成，退出服务");
    }
}
