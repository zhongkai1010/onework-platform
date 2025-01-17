package com.onework.boot.task.collection.core.ctmds;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import com.onework.boot.framework.common.util.json.databind.JsonUtils;
import com.onework.boot.task.collection.core.ScrapeHelper;
import com.onework.boot.task.collection.core.TaskServer;
import com.onework.boot.task.collection.core.TaskServerType;
import com.onework.boot.task.collection.core.ctmds.config.CTMDSDrugConfiguration;
import com.onework.boot.task.collection.core.ctmds.dtos.Institution;
import com.onework.boot.task.collection.core.ctmds.dtos.ResponseResult;
import com.onework.boot.task.collection.core.ctmds.store.CTMDSRecordStore;
import com.onework.boot.task.collection.dao.entity.CTMDSCollectionRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class CTMDSDrugTaskServer extends TaskServer {

    private final CTMDSDrugConfiguration configuration;

    private final CTMDSRecordStore ctmdsRecordStore;

    public CTMDSDrugTaskServer(CTMDSDrugConfiguration configuration, CTMDSRecordStore ctmdsRecordStore) {
        this.configuration = configuration;
        this.ctmdsRecordStore = ctmdsRecordStore;
    }

    @Override
    protected void beforeStart() {
        ctmdsRecordStore.initData();
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CTMDS_DRUG;
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();
        String json = HttpRequest.post(configuration.getUrl()).execute().body();
        ResponseResult responseResult = JsonUtils.parseObject(json, ResponseResult.class);
        log.info("[{}],共有{}页", desc, responseResult.getTotalRows());
        List<Institution> institutions = responseResult.getData();
        int threadCount = configuration.getThreadCount();

        ScrapeHelper.listWorkerExecute(institutions, threadCount, (start, end, items) -> {
            for (int i = 0; i < items.size(); i++) {
                Institution institution = items.get(i);
                CTMDSCollectionRecord ctmdsCollectionRecord;
                boolean isNew = false;
                if (ctmdsRecordStore.isExist(institution.getRecordNo())) {
                    ctmdsCollectionRecord = ctmdsRecordStore.get(institution.getRecordNo());
                } else {
                    isNew = true;
                    ctmdsCollectionRecord = new CTMDSCollectionRecord();
                }
                ctmdsCollectionRecord.setExternalId(institution.getCompanyId());
                ctmdsCollectionRecord.setProvince(institution.getAreaName());
                ctmdsCollectionRecord.setRecordNumber(institution.getRecordNo());
                ctmdsCollectionRecord.setInstitutionName(institution.getCompName());
                ctmdsCollectionRecord.setAddress(institution.getAddress());
                ctmdsCollectionRecord.setContactPerson(institution.getLinkMan());
                ctmdsCollectionRecord.setContactInfo(institution.getLinkTel());
                ctmdsCollectionRecord.setRecordStatus(institution.getRecordStatus());

                try {
                    ctmdsRecordStore.addOrUpdate(ctmdsCollectionRecord);
                    log.info("[{}],第{}项,机构:{},{}成功", desc, start + i, ctmdsCollectionRecord.getInstitutionName(), isNew ? "新增记录" : "更新记录");
                } catch (Exception exception) {
                    log.warn("[{}],第{}项,机构：{},记录失败,错误消息：{}", desc, start + i, ctmdsCollectionRecord.getInstitutionName(), exception.getMessage());
                }
                String html = HttpRequest.get(String.format(configuration.getFileUrl(), institution.getCompanyId())).execute().body();
                String filePathName = String.format("%s\\药-%s.html", configuration.getSavePath(), institution.getCompName());
                FileUtil.writeString(html, filePathName, StandardCharsets.UTF_8);
            }
        });
    }
}
