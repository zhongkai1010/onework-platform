package com.onework.boot.scrape.ctmds;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson2.JSON;
import com.onework.boot.scrape.ITaskServer;
import com.onework.boot.scrape.OneworkScrapeApplication;
import com.onework.boot.scrape.ScrapeConfiguration;
import com.onework.boot.scrape.ScrapeHelper;
import com.onework.boot.scrape.ctmds.dtos.InspectionInfo;
import com.onework.boot.scrape.ctmds.dtos.SpecializationAndResearcher;
import com.onework.boot.scrape.ctmds.store.CTMDSRecordStore;
import com.onework.boot.scrape.data.entity.CTMDSCollectionRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Component
public class CTMDSDrugFileParseTaskServer implements ITaskServer {

    private final ScrapeConfiguration scrapeConfiguration;

    private final CTMDSRecordStore ctmdsRecordStore;

    private static final Logger LOG = LoggerFactory
            .getLogger(OneworkScrapeApplication.class);

    public CTMDSDrugFileParseTaskServer(ScrapeConfiguration scrapeConfiguration, CTMDSRecordStore ctmdsRecordStore) {
        this.scrapeConfiguration = scrapeConfiguration;
        this.ctmdsRecordStore = ctmdsRecordStore;

        this.ctmdsRecordStore.initData();
    }

    @Override
    public void run() {

        List<CTMDSCollectionRecord> records = ctmdsRecordStore.getDrugRecords();
        LOG.info("药物和医疗器械临床试验机构备案系统,药临床机构备字,共{}条", records.size());

        ScrapeHelper.listWorkerExecute(records, 50, (start, end, items) -> {
            for (int i = 1; i <= items.size(); i++) {
                CTMDSCollectionRecord record = items.get(i - 1);
                String filePathName = String.format("%s\\药-%s.html", scrapeConfiguration.getCtmdsSavePath(), record.getInstitutionName());
                boolean exist = FileUtil.exist(filePathName);
                if (!exist) {
                    LOG.info("[{}~{}],第{}项，机构：{}，文件不存在，解析失败", start, end, start + i, record.getInstitutionName());
                    return;
                }
                String html = FileUtil.readUtf8String(filePathName);
                Document document = Jsoup.parse(html);
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
                record.setFirstRecordDate(_getTryLocalDateTime(firstRecordDate));
                // 备案时间
                String recordDate = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(" + (4 + alternateAddressElements.size() + 4) + ") > div").text();
                recordDate = recordDate.replace("(变更)", "");
                record.setRecordDate(_getTryLocalDateTime(recordDate));
                // 备案状态
                String recordStatus = document.select("div > div:nth-of-type(1) > div > div:nth-of-type(" + (4 + alternateAddressElements.size() + 5) + ") > div").text();
                record.setRecordStatus(recordStatus);
                // LOG.info("机构级别:{},其他地址:{},备案时间（首次）:{},备案时间:{},备案状态:{}", institutionLevel, String.join("；", alternateAddress), firstRecordDate, recordDate, recordStatus);

                // 专业与研究者
                Elements specializationAndResearcherElements = document.select("#tabContent2 tr");
                List<SpecializationAndResearcher> specializationAndResearchers = new ArrayList<>();
                for (Element element : specializationAndResearcherElements) {
                    SpecializationAndResearcher specializationAndResearcher = new SpecializationAndResearcher();

                    String specialization = element.select("td:nth-of-type(1)").text();
                    specializationAndResearcher.setSpecialization(specialization);

                    String researcher = element.select("td:nth-of-type(2)").text();
                    specializationAndResearcher.setResearcher(researcher);

                    String professionalTitle = element.select("td:nth-of-type(3)").text();
                    specializationAndResearcher.setProfessionalTitle(professionalTitle);

                    String filingTime = element.select("td:nth-of-type(4)").text();
                    specializationAndResearcher.setFilingTime(filingTime);

                    if (!specialization.isEmpty()) {
                        specializationAndResearchers.add(specializationAndResearcher);
                    }

                }
                record.setSpecializationAndResearchers(JSON.toJSONString(specializationAndResearchers));
                // LOG.info("专业与研究者:{}", JSON.toJSONString(specializationAndResearchers));

                // 检查记录
                Elements inspectionInfoElements = document.select("#tabContent3 tr");
                List<InspectionInfo> inspectionInfos = new ArrayList<>();
                for (Element element : inspectionInfoElements) {
                    InspectionInfo inspectionInfo = new InspectionInfo();

                    String inspectionDate = element.select("td:nth-of-type(1)").text();
                    inspectionInfo.setInspectionDate(inspectionDate);

                    String inspectionCategory = element.select("td:nth-of-type(2)").text();
                    inspectionInfo.setInspectionCategory(inspectionCategory);

                    String inspectionResult = element.select("td:nth-of-type(3)").text();
                    inspectionInfo.setInspectionResult(inspectionResult);

                    String processingStatus = element.select("td:nth-of-type(4)").text();
                    inspectionInfo.setProcessingStatus(processingStatus);

                    if (!inspectionDate.isEmpty()) {
                        inspectionInfos.add(inspectionInfo);
                    }

                }
                record.setInspectionInfo(JSON.toJSONString(inspectionInfos));
                ctmdsRecordStore.updateRecord(record);
                LOG.info("[{}~{}],第{}项，机构：{}，解析成功，更新数据", start, end, start + i, record.getInstitutionName());
            }
        });
    }

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    private static LocalDateTime _getTryLocalDateTime(String value) {
        try {
            return LocalDateTimeUtil.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception exception) {
            return null;
        }
    }
}
