package com.onework.boot.scrape.ctmds.tasks;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ServerConfiguration;
import com.onework.boot.scrape.ctmds.dtos.Institution;
import com.onework.boot.scrape.ctmds.dtos.ResponseResult;
import com.onework.boot.scrape.ctmds.store.CTMDSRecordStore;
import com.onework.boot.scrape.data.entity.CTMDSCollectionRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class ScanListTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final ServerConfiguration serverConfiguration;

    private final CTMDSRecordStore CTMDSRecordStore;

    public ScanListTaskServer(ServerConfiguration serverConfiguration, CTMDSRecordStore CTMDSRecordStore) {
        this.serverConfiguration = serverConfiguration;
        this.CTMDSRecordStore = CTMDSRecordStore;
    }

    @Override
    public void run() {
        String json = HttpRequest.post("https://beian.cfdi.org.cn/CTMDS/pub/PUB010100.do?method=handle06&_dt=20241125171059&pageSize=10000").execute().body();
        ResponseResult responseResult = JSON.to(ResponseResult.class, json);
        LOG.info("获取药物和医疗器械临床试验机构备案系统备案机构数据，共{}条", responseResult.getTotalRows());
        List<Institution> institutions = responseResult.getData();
        for (Institution institution : institutions) {
            try {
                CTMDSCollectionRecord record = CTMDSRecordStore.existRecord(institution.getCompName()) ? CTMDSRecordStore.getRecord(institution.getCompName()) : new CTMDSCollectionRecord();
                record.setExternalId(institution.getCompanyId());
                record.setProvince(institution.getAreaName());
                record.setRecordNumber(institution.getRecordNo());
                record.setInstitutionName(institution.getCompName());
                record.setAddress(institution.getAddress());
                record.setContactPerson(institution.getLinkMan());
                record.setContactInfo(institution.getLinkTel());
                record.setRecordStatus(institution.getRecordStatus());
                if (record.getUid() != null) {
                    CTMDSRecordStore.updateRecord(record);
                    LOG.info("机构：{}，已存在，更新记录", record.getInstitutionName());
                } else {
                    CTMDSRecordStore.insertRecord(record);
                    LOG.info("机构：{}，新增记录", institution.getCompName());
                }
                String html = HttpRequest.get(String.format("https://beian.cfdi.org.cn/CTMDS/pub/PUB010100.do?compId=%s&method=handle07", institution.getCompanyId())).execute().body();
                String filePathName = String.format("%s\\%s.html", serverConfiguration.getCtmdsSavePath(), institution.getCompName());
                FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
            } catch (Exception exception) {
                LOG.info("机构：{}记录失败，错误消息：{}", institution.getCompName(), exception.getMessage());
            }
        }
    }
}
