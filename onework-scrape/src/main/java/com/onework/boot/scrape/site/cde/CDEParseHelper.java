package com.onework.boot.scrape.site.cde;


import com.alibaba.fastjson.JSON;
import com.onework.boot.scrape.dal.dataobject.CDEProject;
import com.onework.boot.scrape.site.ScrapeHelper;
import com.onework.boot.scrape.site.cde.dtos.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CDEParseHelper {

    /**
     *  首次公示信息日期
     * @param doc 文档
     * @return 结果
     */
    public static String getDateOfFirstPublicDisclosure(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(1) > div:nth-of-type(2) > div > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     *  登记号
     * @param doc 文档
     * @return 结果
     */
    public static String getRegistrationNumber(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(1) > div:nth-of-type(2) > div > table > tbody > tr:nth-of-type(1) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 相关登记号
     * @param doc 文档
     * @return 结果
     */
    public static String getRelatedRegistrationNumber(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(2) > td");
        return elements.text();
    }

    /**
     * 药物名称
     * @param doc 文档
     * @return 结果
     */
    public static String getDrugName(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(3) > td");
        String value = elements.text();
        String[] values = value.split("曾用名");
        return values[0].trim();
    }

    /**
     * 曾用名
     * @param doc 文档
     * @return 结果
     */
    public static String getFormerName(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(3) > td");
        String value = elements.text();
        String[] values = value.split("曾用名");
        if (values.length > 1) {
            return values[1].replaceFirst("^:", "").trim();
        } else {
            return "";
        }
    }

    /**
     * 药物类型
     * @param doc 文档
     * @return 结果
     */
    public static String getDrugType(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(4) > td");
        return elements.text();
    }

    /**
     * 临床申请受理号
     * @param doc 文档
     * @return 结果
     */
    public static String getClinicalTrialApplicationAcceptanceNumber(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(5) > td");
        return elements.text();
    }

    /**
     * 适应症
     * @param doc 文档
     * @return 结果
     */
    public static String getIndications(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(6) > td");
        return elements.text();
    }

    /**
     * 试验专业题目
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialProfessionalTitle(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(7) > td");
        return elements.text();
    }


    /**
     * 试验通俗题目
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialPopularTitle(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(8) > td");
        return elements.text();
    }

    /**
     * 试验方案编号
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialProtocolNumber(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(9) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 方案最新版本号
     * @param doc 文档
     * @return 结果
     */
    public static String getLatestProtocolVersionNumber(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(9) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     * 版本日期
     * @param doc 文档
     * @return 结果
     */
    public static String getVersionDate(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(10) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 方案是否为联合用药
     */
    public static String getIsTheProtocolForCombinationTherapy(Document doc) {

        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(1) > tbody > tr:nth-of-type(10) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     *  申请联系人
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getApplicantContact(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(1) > td > div");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> {
            String value = element.select("div > input").val();
            items.add(value);
        });
        return items;
    }

    /**
     * 联系人姓名
     * @param doc 文档
     * @return 结果
     */
    public static String getContactPersonName(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(2) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 联系人座机
     * @param doc 文档
     * @return 结果
     */
    public static String getContactPersonTelephone(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(2) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     * 联系人手机号
     * @param doc 文档
     * @return 结果
     */
    public static String getContactPersonMobilePhone(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(2) > td:nth-of-type(3)");
        return elements.text();
    }

    /**
     * 联系人Email
     * @param doc 文档
     * @return 结果
     */
    public static String getContactPersonEmail(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(3) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 联系人邮政地址
     * @param doc 文档
     * @return 结果
     */
    public static String getContactPersonPostalAddress(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(3) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     * 联系人邮编
     */
    public static String getContactPersonPostalCode(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(2) > tbody > tr:nth-of-type(3) > td:nth-of-type(3)");
        return elements.text();
    }

    /**
     * 试验目的
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialPurpose(Document doc) {
        String html = doc.html();
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*1、试验目的\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        // 查找匹配项
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "";
        }
    }

    /**
     * 试验分类
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialClassification(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(1) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 试验分期
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialPhase(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(1) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     * 设计类型
     * @param doc 文档
     * @return 结果
     */
    public static String getDesignType(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(1) > td:nth-of-type(3)");
        return elements.text();
    }

    /**
     * 随机化
     * @param doc 文档
     * @return 结果
     */
    public static String getRandomization(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(2) > td:nth-of-type(1)");
        return elements.text();
    }

    /**
     * 盲法
     * @param doc 文档
     * @return 结果
     */
    public static String getBlinding(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(2) > td:nth-of-type(2)");
        return elements.text();
    }

    /**
     * 试验范围
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialScope(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(3) > tbody > tr:nth-of-type(2) > td:nth-of-type(3)");
        return elements.text();
    }

    /**
     * 年龄
     * @param doc 文档
     * @return 结果
     */
    public static String getAge(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(4) > tbody > tr:nth-of-type(1) > td");
        return elements.text();
    }

    /**
     * 性别
     * @param doc 文档
     * @return 结果
     */
    public static String getGender(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(4) > tbody > tr:nth-of-type(2) > td");
        return elements.text();
    }

    /**
     * 健康受试者
     * @param doc 文档
     * @return 结果
     */
    public static String getHealthyVolunteers(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(4) > tbody > tr:nth-of-type(3) > td");
        return elements.text();
    }

    /**
     * 入选标准
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getInclusionCriteria(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(4) > tbody > tr:nth-of-type(4) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> items.add(element.select("td:nth-of-type(2)").text()));
        return items;
    }

    /**
     * 排除标准
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getExclusionCriteria(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(4) > tbody > tr:nth-of-type(5) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> items.add(element.select("td:nth-of-type(2)").text()));
        return items;
    }

    /**
     * 试验药
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEMedicine> getTrialMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<CDEMedicine> CDEMedicines = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                CDEMedicine cdeMedicine = new CDEMedicine();
                cdeMedicine.setNumber(number.text());
                cdeMedicine.setName(name.text());
                cdeMedicine.setUsage(usage.text());
                CDEMedicines.add(cdeMedicine);
            }
        });
        return CDEMedicines;
    }

    /**
     * 对照药
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEMedicine> getControlMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<CDEMedicine> CDEMedicines = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                CDEMedicine cdeMedicine = new CDEMedicine();
                cdeMedicine.setNumber(number.text());
                cdeMedicine.setName(name.text());
                cdeMedicine.setUsage(usage.text());
                CDEMedicines.add(cdeMedicine);
            }
        });
        return CDEMedicines;
    }

    /**
     * 主要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEEndpointMetric> getPrimaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<CDEEndpointMetric> cdeEndpointMetrics = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                CDEEndpointMetric CDEEndpointMetric = new CDEEndpointMetric();
                CDEEndpointMetric.setNumber(number.text());
                CDEEndpointMetric.setIndicator(indicator.text());
                CDEEndpointMetric.setEvaluationTime(evaluationTime.text());
                CDEEndpointMetric.setPrimaryEndpoint(primaryEndpoint.text());
                cdeEndpointMetrics.add(CDEEndpointMetric);
            }
        });
        return cdeEndpointMetrics;
    }

    /**
     * 次要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEEndpointMetric> getSecondaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<CDEEndpointMetric> cdeEndpointMetrics = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                CDEEndpointMetric CDEEndpointMetric = new CDEEndpointMetric();
                CDEEndpointMetric.setNumber(number.text());
                CDEEndpointMetric.setIndicator(indicator.text());
                CDEEndpointMetric.setEvaluationTime(evaluationTime.text());
                CDEEndpointMetric.setPrimaryEndpoint(primaryEndpoint.text());
                cdeEndpointMetrics.add(CDEEndpointMetric);
            }
        });
        return cdeEndpointMetrics;
    }

    /**
     * 数据安全监查委员会
     * @param doc 文档
     * @return 结果
     */
    public static String getDataMonitoringCommittee(Document doc) {
        String html = doc.html();
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*6、数据安全监查委员会（DMC）\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        // 查找匹配项
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "";
        }
    }

    /**
     * 是否购买保险
     */
    public static String getInsurancePurchased(Document doc) {
        String html = doc.html();
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*7、是否购买保险\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        // 查找匹配项
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "";
        }
    }

    /**
     * 主要研究者信息
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEPrincipalInvestigator> getPrincipalInvestigators(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*1、主要研究者信息\\s*</div>(.*?)<div class=\"sDPTit2\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<CDEPrincipalInvestigator> CDEPrincipalInvestigators = new ArrayList<>();
            Elements elements = document.select("table");
            elements.forEach(element -> {
                CDEPrincipalInvestigator cdePrincipalInvestigator = new CDEPrincipalInvestigator();
                cdePrincipalInvestigator.setNumber(element.select("tbody > tr:nth-of-type(1) > th:nth-of-type(1)").text());
                cdePrincipalInvestigator.setName(element.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(1)").text());
                cdePrincipalInvestigator.setTitle(element.select("tbody > tr:nth-of-type(1) > td:nth-of-type(3)").text());
                cdePrincipalInvestigator.setDegree(element.select("tbody > tr:nth-of-type(1) > td:nth-of-type(2)").text());
                cdePrincipalInvestigator.setPhone(element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(1)").text());
                cdePrincipalInvestigator.setEmail(element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2)").text());
                cdePrincipalInvestigator.setPostalAddress(element.select(" tbody > tr:nth-of-type(2) > td:nth-of-type(3)").text());
                cdePrincipalInvestigator.setPostalCode(element.select(" tbody > tr:nth-of-type(3) > td:nth-of-type(1)").text());
                cdePrincipalInvestigator.setOrganizationName(element.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2)").text());
                CDEPrincipalInvestigators.add(cdePrincipalInvestigator);
            });
            return CDEPrincipalInvestigators;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 各参加机构信息
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEClinicalInstitution> getClinicalInstitutions(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、各参加机构信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<CDEClinicalInstitution> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    CDEClinicalInstitution item = new CDEClinicalInstitution();
                    item.setNumber(element.select("td:nth-of-type(1)").text());
                    item.setOrganizationName(element.select("td:nth-of-type(2)").text());
                    item.setPrincipal(element.select("td:nth-of-type(3)").text());
                    item.setRegion(element.select("td:nth-of-type(4)").text());
                    item.setProvince(element.select("td:nth-of-type(5)").text());
                    item.setCity(element.select("td:nth-of-type(6)").text());
                    items.add(item);
                }
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 伦理委员会信息
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEEthicsCommittee> getEthicsCommitteeInformation(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*五、伦理委员会信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<CDEEthicsCommittee> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    CDEEthicsCommittee cdeEthicsCommittee = new CDEEthicsCommittee();
                    cdeEthicsCommittee.setNumber(element.select("td:nth-of-type(1)").text());
                    cdeEthicsCommittee.setName(element.select("td:nth-of-type(2)").text());
                    cdeEthicsCommittee.setReviewConclusion(element.select("td:nth-of-type(3)").text());
                    cdeEthicsCommittee.setApprovalDate(element.select("td:nth-of-type(4)").text());
                    items.add(cdeEthicsCommittee);
                }
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     *  试验状态
     * @param doc 文档
     * @return 结果
     */
    public static String getTrialStatus(Document doc) {
        String html = doc.html();
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*1、试验状态\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        // 查找匹配项
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "";
        }
    }

    private static String[] _getDomesticAndForeign(String value) {
        String[] values = new String[2];
        boolean all = value.contains("国内") && value.contains("国际");
        boolean domestic = value.contains("国内") && !value.contains("国际");
        boolean foreign = !value.contains("国内") && value.contains("国际");
        if (all) {
            values = value.split("；");
            values[0] = values[0]
                    .replaceAll("登记人暂未填写该信息", "")
                    .replaceAll("国内", "")
                    .replaceAll(":", "")
                    .replaceAll("：", "")
                    .replaceAll("；", "")
                    .trim();
            values[1] = values[1]
                    .replaceAll("登记人暂未填写该信息", "")
                    .replaceAll("国际", "")
                    .replaceAll(":", "")
                    .replaceAll("：", "")
                    .replaceAll("；", "")
                    .trim();
        } else if (domestic) {
            values[0] = value
                    .replaceAll("登记人暂未填写该信息", "")
                    .replaceAll("国内", "")
                    .replaceAll(":", "")
                    .replaceAll("：", "")
                    .replaceAll("；", "")
                    .trim();
            values[1] = "";
        } else if (foreign) {
            values[0] = "";
            values[1] = value
                    .replaceAll("登记人暂未填写该信息", "")
                    .replaceAll("国际", "")
                    .replaceAll(":", "")
                    .replaceAll("：", "")
                    .replaceAll("；", "")
                    .trim();
        } else {
            values[0] = "";
            values[1] = "";
        }
        return values;
    }

    /**
     *  试验人数
     * @param doc 文档
     * @return 结果
     */
    public static CDEParticipantsNumber getNumberOfParticipants(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、试验人数\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            CDEParticipantsNumber cdeParticipantsNumber = new CDEParticipantsNumber();
            // 目标入组人数
            String targetEnrollmentNumber = document.select("tr:nth-of-type(1) > td").text().trim();
            String[] targetEnrollmentNumberValues = _getDomesticAndForeign(targetEnrollmentNumber);
            cdeParticipantsNumber.setTargetEnrollmentDomestic(targetEnrollmentNumberValues[0]);
            cdeParticipantsNumber.setTargetEnrollmentForeign(targetEnrollmentNumberValues[1]);

            // 已入组人数
            String numberEnrolled = document.select("tr:nth-of-type(2) > td").text().trim();
            String[] numberEnrolledValues = _getDomesticAndForeign(numberEnrolled);
            cdeParticipantsNumber.setCurrentEnrollmentDomestic(numberEnrolledValues[0]);
            cdeParticipantsNumber.setCurrentEnrollmentForeign(numberEnrolledValues[1]);

            // 实际入组总人数
            String totalNumberOfParticipantsEnrolled = document.select("tr:nth-of-type(3) > td").text().trim();
            String[] totalNumberOfParticipantsEnrolledValues = _getDomesticAndForeign(totalNumberOfParticipantsEnrolled);
            cdeParticipantsNumber.setTotalActualEnrollmentDomestic(totalNumberOfParticipantsEnrolledValues[0]);
            cdeParticipantsNumber.setTotalActualEnrollmentForeign(totalNumberOfParticipantsEnrolledValues[1]);

            return cdeParticipantsNumber;
        } else {
            return new CDEParticipantsNumber();
        }
    }

    /**
     *  试验日期
     * @param doc 文档
     * @return 结果
     */
    public static CDETrialCompletionDate getSubjectRecruitmentAndTrialCompletionDate(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*3、受试者招募及试验完成日期\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            CDETrialCompletionDate cdeTrialCompletionDate = new CDETrialCompletionDate();

            // 第一例受试者签署知情同意书日期
            String dateOfFirstSubjectsInformedConsent = document.select("tr:nth-of-type(1) > td").text();
            String[] dateOfFirstSubjectsInformedConsentValues = _getDomesticAndForeign(dateOfFirstSubjectsInformedConsent);
            cdeTrialCompletionDate.setFirstConsentDateDomestic(dateOfFirstSubjectsInformedConsentValues[0]);
            cdeTrialCompletionDate.setFirstConsentDateForeign(dateOfFirstSubjectsInformedConsentValues[1]);

            // 第一例受试者入组日期
            String dateOfFirstSubjectEnrolled = document.select("tr:nth-of-type(2) > td").text();
            String[] dateOfFirstSubjectEnrolledValues = _getDomesticAndForeign(dateOfFirstSubjectEnrolled);
            cdeTrialCompletionDate.setFirstEnrollmentDateDomestic(dateOfFirstSubjectEnrolledValues[0]);
            cdeTrialCompletionDate.setFirstEnrollmentDateForeign(dateOfFirstSubjectEnrolledValues[1]);

            // 完成日期
            String completionDate = document.select("tr:nth-of-type(3) > td").text();
            String[] completionDateValues = _getDomesticAndForeign(completionDate);
            cdeTrialCompletionDate.setCompletionDateDomestic(completionDateValues[0]);
            cdeTrialCompletionDate.setCompletionDateForeign(completionDateValues[1]);

            return cdeTrialCompletionDate;
        } else {
            return new CDETrialCompletionDate();
        }
    }

    /**
     * 临床试验结果摘要
     * @param doc 文档
     * @return 结果
     */
    public static List<CDEClinicalTrialResultsSummary> getTrialResultsSummary(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*七、临床试验结果摘要\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<CDEClinicalTrialResultsSummary> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    CDEClinicalTrialResultsSummary cdeClinicalTrialResultsSummary = new CDEClinicalTrialResultsSummary();
                    String number = element.select("td:nth-of-type(1)").text();
                    String versionNumber = element.select("td:nth-of-type(2)").text();
                    String versionDate = element.select("td:nth-of-type(3)").text();
                    cdeClinicalTrialResultsSummary.setNumber(number);
                    cdeClinicalTrialResultsSummary.setVersionNumber(versionNumber);
                    cdeClinicalTrialResultsSummary.setVersionDate(versionDate);
                    items.add(cdeClinicalTrialResultsSummary);
                }
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 解析文件，创建CDE项目
     * @param doc 文件
     * @return CDE项目
     */
    public static CDEProject createProject(Document doc) {
        CDEProject project = new CDEProject();
        // 基本信息
        // 首次公示信息日期
        project.setFirstPublicInfoDate(ScrapeHelper.getTryLocalDateTime(CDEParseHelper.getDateOfFirstPublicDisclosure(doc)));
        //（一）题目和背景信息
        // 1．登记号（不可更新项、公示项）
        project.setRegistrationNumber(CDEParseHelper.getRegistrationNumber(doc));
        // 2．相关登记号（可更新项、公示项）
        project.setRelatedRegistrationNumber(CDEParseHelper.getRelatedRegistrationNumber(doc));
        // 3．药物名称*（不可更新项、选择公示项）
        project.setDrugName(CDEParseHelper.getDrugName(doc));
        project.setFormerName(CDEParseHelper.getFormerName(doc));
        // 4．曾用名（可更新项、选择公示项）
        // 5．药物类型*（不可更新项、公示项）
        project.setDrugType(CDEParseHelper.getDrugType(doc));
        // 6．受理号/备案号*（不可更新项、选择公示项）
        project.setAcceptanceNumber(CDEParseHelper.getClinicalTrialApplicationAcceptanceNumber(doc));
        // 7．批件号（备案号）/批准日期（默示许可日期/备案日期）*（不可更新项、不公示项）
        // 8．适应症*（不可更新项、公示项）
        project.setIndication(CDEParseHelper.getIndications(doc));
        // 9．试验专业题目*（不可更新项、公示项）
        project.setProfessionalTitle(CDEParseHelper.getTrialProfessionalTitle(doc));
        // 10．试验通俗题目*（可更新项、公示项）
        project.setPopularTitle(CDEParseHelper.getTrialPopularTitle(doc));
        // 11．试验方案编号*（不可更新项、公示项）
        project.setTrialProtocolNumber(CDEParseHelper.getTrialProtocolNumber(doc));
        // 12．试验方案编号重复原因（可更新项、不公示项）
        // 13．方案最新版本号*（可更新项、公示项）
        project.setLatestProtocolVersion(CDEParseHelper.getLatestProtocolVersionNumber(doc));
        // 14．版本日期*（可更新项、公示项）
        project.setVersionDate(CDEParseHelper.getVersionDate(doc));
        // 15．是否联合用药*（不可更新项、公示项）
        project.setCombinedMedication(CDEParseHelper.getIsTheProtocolForCombinationTherapy(doc));
        //（二）申请人信息
        // 1．申请人名称（不可更新项、公示项）
        project.setApplicantName(JSON.toJSONString(CDEParseHelper.getApplicantContact(doc)));
        // 2．联系人姓名*（可更新项、公示项）
        project.setContactName(CDEParseHelper.getContactPersonName(doc));
        // 3．联系人座机*（可更新项、公示项）
        project.setContactLandline(CDEParseHelper.getContactPersonTelephone(doc));
        // 4．联系人手机号（可更新项、公示项）
        project.setContactMobile(CDEParseHelper.getContactPersonMobilePhone(doc));
        // 5．联系人 Email*（可更新项、公示项）
        project.setContactEmail(CDEParseHelper.getContactPersonEmail(doc));
        // 6．联系人邮政地址*（可更新项、公示项）
        project.setContactAddress(CDEParseHelper.getContactPersonPostalAddress(doc));
        // 7．联系人邮编*（可更新项、公示项）
        project.setContactZipCode(CDEParseHelper.getContactPersonPostalCode(doc));
        // 8．经费来源*（可更新项、不公示项）
        //（三）临床试验信息
        // 1. 试验目的*（不可更新项、公示项）
        project.setTrialPurpose(CDEParseHelper.getTrialPurpose(doc));
        // 2.1 试验分类（不可更新项、公示项）
        project.setTrialClassification(CDEParseHelper.getTrialClassification(doc));
        // 2.2 试验分期（不可更新项、公示项）
        project.setTrialPhase(CDEParseHelper.getTrialPhase(doc));
        // 2.3 设计类型（不可更新项、公示项）
        project.setDesignType(CDEParseHelper.getDesignType(doc));
        // 2.4 随机化（不可更新项、公示项）
        project.setRandomization(CDEParseHelper.getRandomization(doc));
        // 2.5 盲法（不可更新项、公示项）
        project.setBlinding(CDEParseHelper.getBlinding(doc));
        // 2.6 试验范围（不可更新项、公示项）
        project.setTrialScope(CDEParseHelper.getTrialScope(doc));
        // 3. 受试者信息
        // 3.1 年龄*（不可更新项、公示项）
        project.setAge(CDEParseHelper.getAge(doc));
        // 3.2 性别*（不可更新项、公示项）
        project.setGender(CDEParseHelper.getGender(doc));
        // 3.3 健康受试者*（不可更新项、公示项）
        project.setHealthyVolunteers(CDEParseHelper.getHealthyVolunteers(doc));
        // 3.4 入选标准*（可更新项、公示项）
        project.setInclusionCriteria(JSON.toJSONString(CDEParseHelper.getInclusionCriteria(doc)));
        // 3.5 排除标准*（可更新项、公示项）
        project.setExclusionCriteria(JSON.toJSONString(CDEParseHelper.getExclusionCriteria(doc)));
        // 4. 试验分组
        // 4.1 试验药*
        project.setTestDrug(JSON.toJSONString(CDEParseHelper.getTrialMedication(doc)));
        // 4.1.1 名称（不可更新项、公示项）
        // 4.1.2 生产信息/批号（不可更新项、不公示项）
        // 4.1.3 用法（不可更新项、公示项）
        // 4.2 对照药
        project.setControlDrug(JSON.toJSONString(CDEParseHelper.getControlMedication(doc)));
        // 4.2.1 名称（不可更新项、公示项）
        // 4.2.2 生产信息/批号（不可更新项、不公示项）
        // 4.2.3 用法（不可更新项、公示项）
        // 5. 终点指标
        // 5.1 主要终点指标及评价时间*（不可更新项、公示项）
        project.setPrimaryEndpoints(JSON.toJSONString(CDEParseHelper.getPrimaryEndpointAndTimingOfAssessment(doc)));
        // 5.2 次要终点指标及评价时间（不可更新项、公示项）
        project.setSecondaryEndpoints(JSON.toJSONString(CDEParseHelper.getSecondaryEndpointAndTimingOfAssessment(doc)));
        // 6. 数据安全监查委员会（DMC）*（可更新项、公示项）
        project.setDataMonitoringCommittee(CDEParseHelper.getDataMonitoringCommittee(doc));
        // 7. 为受试者购买试验伤害保险*（可更新项、公示项）
        project.setInsuranceForSubjects(CDEParseHelper.getInsurancePurchased(doc));
        //（四）研究者信息
        // 1. 主要研究者信息（可更新项、公示项）
        project.setPrincipalInvestigatorInfo(JSON.toJSONString(CDEParseHelper.getPrincipalInvestigators(doc)));
        // ●姓名*
        // ●学位：
        // ●职称：
        // ●电话：
        // ●Email：
        // ●邮政地址
        // ●邮编
        // ●单位名称
        // 2. 各参加机构信息（可更新项、公示项）
        project.setParticipatingInstitutionsInfo(JSON.toJSONString(CDEParseHelper.getClinicalInstitutions(doc)));
        // ●机构名称：
        // ●（主要）研究者
        // ●国家或地区：
        // ●省（州）：
        // ●城市
        //（五）伦理委员会信息*（不可更新项、公示项）
        project.setEthicsCommitteeInfo(JSON.toJSONString(CDEParseHelper.getEthicsCommitteeInformation(doc)));
        // ●名称：
        // ●审查结论
        // ●伦理批件/备案证明文件
        // ●批准日期/备案日期：
        // ●知情同意书：
        // ●成员姓名：
        // ●IRB/EC

        //（六）试验状态信息
        // 1. 试验状态*（可更新项、公示项）
        project.setTrialStatus(CDEParseHelper.getTrialStatus(doc));
        // 2. 试验人数
        CDEParticipantsNumber cdeParticipantsNumber = CDEParseHelper.getNumberOfParticipants(doc);
        // 2.1 目标入组人数
        project.setTargetEnrollmentDomestic(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getTargetEnrollmentDomestic()));
        project.setTargetEnrollmentForeign(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getTargetEnrollmentForeign()));
        // 2.3 已入组人数
        project.setCurrentEnrollmentDomestic(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getCurrentEnrollmentDomestic()));
        project.setCurrentEnrollmentForeign(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getCurrentEnrollmentForeign()));
        // 2.5 实际入组总人数
        project.setTotalActualEnrollmentDomestic(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getTotalActualEnrollmentDomestic()));
        project.setTotalActualEnrollmentForeign(ScrapeHelper.getTryInteger(cdeParticipantsNumber.getTotalActualEnrollmentForeign()));
        // 3. 受试者招募及试验完成日期
        CDETrialCompletionDate CDETrialCompletionDate = CDEParseHelper.getSubjectRecruitmentAndTrialCompletionDate(doc);
        // 第一例受试者签署知情同意书日期
        project.setFirstConsentDateDomestic(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getFirstConsentDateDomestic()));
        project.setFirstConsentDateForeign(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getFirstEnrollmentDateForeign()));
        // 第一例受试者入组日期
        project.setFirstEnrollmentDateDomestic(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getFirstEnrollmentDateDomestic()));
        project.setFirstEnrollmentDateForeign(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getFirstEnrollmentDateForeign()));
        // 试验完成日期
        // 试验暂停日期
        // 试验终止日期
        project.setCompletionDateDomestic(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getCompletionDateDomestic()));
        project.setCompletionDateForeign(ScrapeHelper.getTryLocalDateTime(CDETrialCompletionDate.getCompletionDateForeign()));
        //（八）临床试验结果摘要（不可更新项、公示项）
        project.setClinicalTrialResultsSummary(JSON.toJSONString(CDEParseHelper.getTrialResultsSummary(doc)));

        return project;
    }
}
