package com.onework.boot.module.scraper.task.ctmds;

import cn.hutool.core.io.FileUtil;
import com.onework.boot.framework.common.util.json.JsonUtils;

import com.onework.boot.module.scraper.core.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.module.scraper.task.ScrapeHelper;
import com.onework.boot.module.scraper.task.TaskServer;
import com.onework.boot.module.scraper.task.TaskServerType;
import com.onework.boot.module.scraper.task.ctmds.config.CTMDSInstrumentFileConfiguration;
import com.onework.boot.module.scraper.task.ctmds.dtos.SpecializationAndResearcher;
import com.onework.boot.module.scraper.task.ctmds.store.CTMDSRecordStore;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CTMDSInstrumentFileTaskServer extends TaskServer {

    private final CTMDSInstrumentFileConfiguration configuration;

    private final CTMDSRecordStore ctmdsRecordStore;

    public CTMDSInstrumentFileTaskServer(CTMDSInstrumentFileConfiguration configuration, CTMDSRecordStore ctmdsRecordStore) {
        this.configuration = configuration;
        this.ctmdsRecordStore = ctmdsRecordStore;
    }

    @Override
    public TaskServerType getTaskServerType() {
        return TaskServerType.CTMDS_INSTRUMENT_FILE;
    }

    @Override
    public void run() {
        String desc = getTaskServerType().getDescription();
        List<CTMDSCollectionRecord> records = ctmdsRecordStore.getInstrumentRecords();
        log.info("[{}],共有{}页", desc, records.size());

        int threadCount = configuration.getThreadCount();
        ScrapeHelper.listWorkerExecute(records, threadCount, (start, end, items) -> {
            for (int i = 1; i <= items.size(); i++) {
                CTMDSCollectionRecord record = items.get(i - 1);
                String filePathName = String.format("%s\\械-%s.html", configuration.getSavePath(), record.getInstitutionName());
                boolean exist = FileUtil.exist(filePathName);
                if (!exist) {
                    log.warn("[{}],第{}项,机构:{},文件不存在,解析失败", desc, start + i, record.getInstitutionName());
                    return;
                }
                String html = FileUtil.readUtf8String(filePathName);
                Document document = Jsoup.parse(html);
                parseDocument(document, record);
                try {
                    ctmdsRecordStore.addOrUpdate(record);
                    log.info("[{}],第{}项,机构:{},解析成功", desc, start + i, record.getInstitutionName());
                } catch (Exception exception) {
                    log.warn("[{}],第{}项,机构:{},解析失败,错误消息:{}", desc, start + i, record.getInstitutionName(), exception.getMessage());
                }
            }
        });
    }

    private void parseDocument(@NotNull Document document, @NotNull CTMDSCollectionRecord record) {
        // 机构级别
        String institutionLevel = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(3) > div").text();
        record.setInstitutionLevel(institutionLevel);
        // 其他地址
        List<String> alternateAddress = new ArrayList<>();
        Elements alternateAddressElements = document.select("[class*=addressList]");
        for (Element element : alternateAddressElements) {
            alternateAddress.add(element.select("div > div").text());
        }
        record.setAlternateAddress(String.join("；", alternateAddress));
        // 备案时间（首次）
        String firstRecordDate = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(" + (4 + alternateAddressElements.size() + 3) + ") > div").text();
        record.setFirstRecordDate(ScrapeHelper.getTryLocalDateTime(firstRecordDate));
        // 备案时间
        String recordDate = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(" + (4 + alternateAddressElements.size() + 4) + ") > div").text();
        recordDate = recordDate.replace("(变更)", "");
        record.setRecordDate(ScrapeHelper.getTryLocalDateTime(recordDate));
        // 备案状态
        String recordStatus = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(" + (4 + alternateAddressElements.size() + 5) + ") > div").text();
        record.setRecordStatus(recordStatus);
        // LOG.info("机构级别:{},其他地址:{},备案时间（首次）:{},备案时间:{},备案状态:{}", institutionLevel, String.join("；", alternateAddress), firstRecordDate, recordDate, recordStatus);

        // 专业与研究者
        Elements specializationAndResearcherElements = document.select("tbody tr");
        List<SpecializationAndResearcher> specializationAndResearchers = new ArrayList<>();
        for (Element element : specializationAndResearcherElements) {
            SpecializationAndResearcher specializationAndResearcher = new SpecializationAndResearcher();

            String specialization = element.select("td:nth-of-type(1)").text();
            specializationAndResearcher.setSpecialization(specialization);

            String researcher = element.select("td:nth-of-type(2)").text();
            specializationAndResearcher.setResearcher(researcher);

            String professionalTitle = element.select("td:nth-of-type(3)").text();
            specializationAndResearcher.setProfessionalTitle(professionalTitle);

            if (!specialization.isEmpty()) {
                specializationAndResearchers.add(specializationAndResearcher);
            }
        }
        record.setSpecializationAndResearchers(JsonUtils.toJsonString(specializationAndResearchers));
        // LOG.info("专业与研究者:{}", JSON.toJSONString(specializationAndResearchers));
    }
}
