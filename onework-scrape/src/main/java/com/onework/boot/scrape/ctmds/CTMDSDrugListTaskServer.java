package com.onework.boot.scrape.ctmds;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
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
public class CTMDSDrugListTaskServer implements ITaskServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    private final ScrapeConfiguration scrapeConfiguration;

    private final CTMDSRecordStore ctmdsRecordStore;

    public CTMDSDrugListTaskServer(ScrapeConfiguration scrapeConfiguration, CTMDSRecordStore ctmdsRecordStore) {
        this.scrapeConfiguration = scrapeConfiguration;
        this.ctmdsRecordStore = ctmdsRecordStore;

        this.ctmdsRecordStore.initData();
    }

    @Override
    public void run() {
        String json = HttpRequest.post("https://beian.cfdi.org.cn/CTMDS/pub/PUB010100.do?method=handle06&pageSize=10000").execute().body();
        ResponseResult responseResult = JSON.to(ResponseResult.class, json);
        LOG.info("获取药物和医疗器械临床试验机构备案系统备案机构数据，共{}条", responseResult.getTotalRows());
        List<Institution> institutions = responseResult.getData();
        ScrapeHelper.listWorkerExecute(institutions, 50, (start, end, items) -> {
            for (int i = 0; i < items.size(); i++) {
                Institution institution = items.get(i);
                try {
                    if (ctmdsRecordStore.existRecord(institution.getCompName())) {
                        CTMDSCollectionRecord record = ctmdsRecordStore.getRecord(institution.getCompName());
                        record.setExternalId(institution.getCompanyId());
                        record.setProvince(institution.getAreaName());
                        record.setRecordNumber(institution.getRecordNo());
                        record.setInstitutionName(institution.getCompName());
                        record.setAddress(institution.getAddress());
                        record.setContactPerson(institution.getLinkMan());
                        record.setContactInfo(institution.getLinkTel());
                        record.setRecordStatus(institution.getRecordStatus());
                        ctmdsRecordStore.updateRecord(record);
                        LOG.info("[{}~{}],第{}项，机构：{}，已存在，更新记录", start, end, start + i, record.getInstitutionName());
                    } else {
                        CTMDSCollectionRecord record = new CTMDSCollectionRecord();
                        record.setExternalId(institution.getCompanyId());
                        record.setProvince(institution.getAreaName());
                        record.setRecordNumber(institution.getRecordNo());
                        record.setInstitutionName(institution.getCompName());
                        record.setAddress(institution.getAddress());
                        record.setContactPerson(institution.getLinkMan());
                        record.setContactInfo(institution.getLinkTel());
                        record.setRecordStatus(institution.getRecordStatus());
                        ctmdsRecordStore.insertRecord(record);
                        LOG.info("[{}~{}],第{}项，机构：{}，新增记录", start, end, start + i, record.getInstitutionName());
                    }
                    String html = HttpRequest.get(String.format("https://beian.cfdi.org.cn/CTMDS/pub/PUB010100.do?compId=%s&method=handle07", institution.getCompanyId())).execute().body();
                    String filePathName = String.format("%s\\药-%s.html", scrapeConfiguration.getCtmdsSavePath(), institution.getCompName());
                    FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
                } catch (Exception exception) {
                    LOG.info("[{}~{}],第{}项，机构：{}，错误消息：{}", start, end, start + i, institution.getCompName(), exception.getMessage());
                }
            }
        });
    }
}
