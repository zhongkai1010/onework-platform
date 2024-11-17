package com.onework.boot.cde.collection;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSON;
import com.onework.boot.cde.collection.dtos.*;
import com.onework.boot.data.entity.CDEProject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParseHelper {

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
    public static List<Medicine> getTrialMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<Medicine> medicines = new ArrayList<>();

        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                Medicine medicine = new Medicine();
                medicine.setNumber(number.text());
                medicine.setName(name.text());
                medicine.setUsage(usage.text());
                medicines.add(medicine);
            }
        });
        return medicines;
    }

    /**
     * 对照药
     * @param doc 文档
     * @return 结果
     */
    public static List<Medicine> getControlMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<Medicine> medicines = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                Medicine medicine = new Medicine();
                medicine.setNumber(number.text());
                medicine.setName(name.text());
                medicine.setUsage(usage.text());
                medicines.add(medicine);
            }
        });
        return medicines;
    }

    /**
     * 主要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<EndpointMetric> getPrimaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<EndpointMetric> endpointMetrics = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                EndpointMetric endpointMetric = new EndpointMetric();
                endpointMetric.setNumber(number.text());
                endpointMetric.setIndicator(indicator.text());
                endpointMetric.setEvaluationTime(evaluationTime.text());
                endpointMetric.setPrimaryEndpoint(primaryEndpoint.text());
                endpointMetrics.add(endpointMetric);
            }
        });
        return endpointMetrics;
    }

    /**
     * 次要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<EndpointMetric> getSecondaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<EndpointMetric> endpointMetrics = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                EndpointMetric endpointMetric = new EndpointMetric();
                endpointMetric.setNumber(number.text());
                endpointMetric.setIndicator(indicator.text());
                endpointMetric.setEvaluationTime(evaluationTime.text());
                endpointMetric.setPrimaryEndpoint(primaryEndpoint.text());
                endpointMetrics.add(endpointMetric);
            }
        });
        return endpointMetrics;
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
    public static List<PrincipalInvestigatorInformation> getPrincipalInvestigatorInformation(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*1、主要研究者信息\\s*</div>(.*?)<div class=\"sDPTit2\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<PrincipalInvestigatorInformation> principalInvestigatorInformations = new ArrayList<>();
            Elements elements = document.select("table");
            elements.forEach(element -> {
                PrincipalInvestigatorInformation principalInvestigatorInformation = new PrincipalInvestigatorInformation();
                principalInvestigatorInformation.setNumber(element.select("tbody > tr:nth-of-type(1) > th:nth-of-type(1)").text());
                principalInvestigatorInformation.setName(element.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(1)").text());
                principalInvestigatorInformation.setDegree(element.select("tbody > tr:nth-of-type(1) > td:nth-of-type(2)").text());
                principalInvestigatorInformation.setPhone(element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(1)").text());
                principalInvestigatorInformation.setEmail(element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2)").text());
                principalInvestigatorInformation.setPostalAddress(element.select(" tbody > tr:nth-of-type(2) > td:nth-of-type(3)").text());
                principalInvestigatorInformation.setPostalCode(element.select(" tbody > tr:nth-of-type(3) > td:nth-of-type(1)").text());
                principalInvestigatorInformation.setOrganizationName(element.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2)").text());
                principalInvestigatorInformations.add(principalInvestigatorInformation);
            });
            return principalInvestigatorInformations;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 各参加机构信息
     * @param doc 文档
     * @return 结果
     */
    public static List<ParticipatingOrganizationsInformation> getInformationOnParticipatingInstitutions(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、各参加机构信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<ParticipatingOrganizationsInformation> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    ParticipatingOrganizationsInformation item = new ParticipatingOrganizationsInformation();
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
    public static List<EthicsCommittee> getEthicsCommitteeInformation(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*五、伦理委员会信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<EthicsCommittee> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    EthicsCommittee ethicsCommittee = new EthicsCommittee();
                    ethicsCommittee.setNumber(element.select("td:nth-of-type(1)").text());
                    ethicsCommittee.setName(element.select("td:nth-of-type(2)").text());
                    ethicsCommittee.setReviewConclusion(element.select("td:nth-of-type(3)").text());
                    ethicsCommittee.setApprovalDate(element.select("td:nth-of-type(4)").text());
                    items.add(ethicsCommittee);
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
    public static ParticipantsNumber getNumberOfParticipants(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、试验人数\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            ParticipantsNumber participantsNumber = new ParticipantsNumber();
            // 目标入组人数
            String targetEnrollmentNumber = document.select("tr:nth-of-type(1) > td").text().trim();
            String[] targetEnrollmentNumberValues = _getDomesticAndForeign(targetEnrollmentNumber);
            participantsNumber.setTargetEnrollmentDomestic(targetEnrollmentNumberValues[0]);
            participantsNumber.setTargetEnrollmentForeign(targetEnrollmentNumberValues[1]);

            // 已入组人数
            String numberEnrolled = document.select("tr:nth-of-type(2) > td").text().trim();
            String[] numberEnrolledValues = _getDomesticAndForeign(numberEnrolled);
            participantsNumber.setCurrentEnrollmentDomestic(numberEnrolledValues[0]);
            participantsNumber.setCurrentEnrollmentForeign(numberEnrolledValues[1]);

            // 实际入组总人数
            String totalNumberOfParticipantsEnrolled = document.select("tr:nth-of-type(3) > td").text().trim();
            String[] totalNumberOfParticipantsEnrolledValues = _getDomesticAndForeign(totalNumberOfParticipantsEnrolled);
            participantsNumber.setTotalActualEnrollmentDomestic(totalNumberOfParticipantsEnrolledValues[0]);
            participantsNumber.setTotalActualEnrollmentForeign(totalNumberOfParticipantsEnrolledValues[1]);

            return participantsNumber;
        } else {
            return new ParticipantsNumber();
        }
    }

    /**
     *  试验日期
     * @param doc 文档
     * @return 结果
     */
    public static TrialCompletionDate getSubjectRecruitmentAndTrialCompletionDate(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*3、受试者招募及试验完成日期\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            TrialCompletionDate trialCompletionDate = new TrialCompletionDate();

            // 第一例受试者签署知情同意书日期
            String dateOfFirstSubjectsInformedConsent = document.select("tr:nth-of-type(1) > td").text();
            String[] dateOfFirstSubjectsInformedConsentValues = _getDomesticAndForeign(dateOfFirstSubjectsInformedConsent);
            trialCompletionDate.setFirstConsentDateDomestic(dateOfFirstSubjectsInformedConsentValues[0]);
            trialCompletionDate.setFirstConsentDateForeign(dateOfFirstSubjectsInformedConsentValues[1]);

            // 第一例受试者入组日期
            String dateOfFirstSubjectEnrolled = document.select("tr:nth-of-type(2) > td").text();
            String[] dateOfFirstSubjectEnrolledValues = _getDomesticAndForeign(dateOfFirstSubjectEnrolled);
            trialCompletionDate.setFirstEnrollmentDateDomestic(dateOfFirstSubjectEnrolledValues[0]);
            trialCompletionDate.setFirstEnrollmentDateForeign(dateOfFirstSubjectEnrolledValues[1]);

            // 完成日期
            String completionDate = document.select("tr:nth-of-type(3) > td").text();
            String[] completionDateValues = _getDomesticAndForeign(completionDate);
            trialCompletionDate.setCompletionDateDomestic(completionDateValues[0]);
            trialCompletionDate.setCompletionDateForeign(completionDateValues[1]);

            return trialCompletionDate;
        } else {
            return new TrialCompletionDate();
        }
    }

    /**
     * 临床试验结果摘要
     * @param doc 文档
     * @return 结果
     */
    public static List<ClinicalTrialResultsSummary> getTrialResultsSummary(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*七、临床试验结果摘要\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<ClinicalTrialResultsSummary> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    ClinicalTrialResultsSummary clinicalTrialResultsSummary = new ClinicalTrialResultsSummary();
                    String number = element.select("td:nth-of-type(1)").text();
                    String versionNumber = element.select("td:nth-of-type(2)").text();
                    String versionDate = element.select("td:nth-of-type(3)").text();
                    clinicalTrialResultsSummary.setNumber(number);
                    clinicalTrialResultsSummary.setVersionNumber(versionNumber);
                    clinicalTrialResultsSummary.setVersionDate(versionDate);
                    items.add(clinicalTrialResultsSummary);
                }
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     *  处理异常值
     * @param value 值
     * @return 数值
     */
    private static Integer getTryInteger(String value) {
        try {
            return Convert.toInt(value);
        } catch (Exception exception) {
            return null;
        }
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


    /**
     * 解析文件，创建CDE项目
     * @param doc 文件
     * @return CDE项目
     */
    public static CDEProject createProject(Document doc) {
        CDEProject project = new CDEProject();
        // 基本信息
        // 首次公示信息日期
        project.setFirstPublicInfoDate(_getTryLocalDateTime(FileParseHelper.getDateOfFirstPublicDisclosure(doc)));
        //（一）题目和背景信息
        // 1．登记号（不可更新项、公示项）
        project.setRegistrationNumber(FileParseHelper.getRegistrationNumber(doc));
        // 2．相关登记号（可更新项、公示项）
        project.setRelatedRegistrationNumber(FileParseHelper.getRelatedRegistrationNumber(doc));
        // 3．药物名称*（不可更新项、选择公示项）
        project.setDrugName(FileParseHelper.getDrugName(doc));
        project.setFormerName(FileParseHelper.getFormerName(doc));
        // 4．曾用名（可更新项、选择公示项）
        // 5．药物类型*（不可更新项、公示项）
        project.setDrugType(FileParseHelper.getDrugType(doc));
        // 6．受理号/备案号*（不可更新项、选择公示项）
        project.setAcceptanceNumber(FileParseHelper.getClinicalTrialApplicationAcceptanceNumber(doc));
        // 7．批件号（备案号）/批准日期（默示许可日期/备案日期）*（不可更新项、不公示项）
        // 8．适应症*（不可更新项、公示项）
        project.setIndication(FileParseHelper.getIndications(doc));
        // 9．试验专业题目*（不可更新项、公示项）
        project.setProfessionalTitle(FileParseHelper.getTrialProfessionalTitle(doc));
        // 10．试验通俗题目*（可更新项、公示项）
        project.setPopularTitle(FileParseHelper.getTrialPopularTitle(doc));
        // 11．试验方案编号*（不可更新项、公示项）
        project.setTrialProtocolNumber(FileParseHelper.getTrialProtocolNumber(doc));
        // 12．试验方案编号重复原因（可更新项、不公示项）
        // 13．方案最新版本号*（可更新项、公示项）
        project.setLatestProtocolVersion(FileParseHelper.getLatestProtocolVersionNumber(doc));
        // 14．版本日期*（可更新项、公示项）
        project.setVersionDate(FileParseHelper.getVersionDate(doc));
        // 15．是否联合用药*（不可更新项、公示项）
        project.setCombinedMedication(FileParseHelper.getIsTheProtocolForCombinationTherapy(doc));
        //（二）申请人信息
        // 1．申请人名称（不可更新项、公示项）
        project.setApplicantName(JSON.toJSONString(FileParseHelper.getApplicantContact(doc)));
        // 2．联系人姓名*（可更新项、公示项）
        project.setContactName(FileParseHelper.getContactPersonName(doc));
        // 3．联系人座机*（可更新项、公示项）
        project.setContactLandline(FileParseHelper.getContactPersonTelephone(doc));
        // 4．联系人手机号（可更新项、公示项）
        project.setContactMobile(FileParseHelper.getContactPersonMobilePhone(doc));
        // 5．联系人 Email*（可更新项、公示项）
        project.setContactEmail(FileParseHelper.getContactPersonEmail(doc));
        // 6．联系人邮政地址*（可更新项、公示项）
        project.setContactAddress(FileParseHelper.getContactPersonPostalAddress(doc));
        // 7．联系人邮编*（可更新项、公示项）
        project.setContactZipCode(FileParseHelper.getContactPersonPostalCode(doc));
        // 8．经费来源*（可更新项、不公示项）
        //（三）临床试验信息
        // 1. 试验目的*（不可更新项、公示项）
        project.setTrialPurpose(FileParseHelper.getTrialPurpose(doc));
        // 2.1 试验分类（不可更新项、公示项）
        project.setTrialClassification(FileParseHelper.getTrialClassification(doc));
        // 2.2 试验分期（不可更新项、公示项）
        project.setTrialPhase(FileParseHelper.getTrialPhase(doc));
        // 2.3 设计类型（不可更新项、公示项）
        project.setDesignType(FileParseHelper.getDesignType(doc));
        // 2.4 随机化（不可更新项、公示项）
        project.setRandomization(FileParseHelper.getRandomization(doc));
        // 2.5 盲法（不可更新项、公示项）
        project.setBlinding(FileParseHelper.getBlinding(doc));
        // 2.6 试验范围（不可更新项、公示项）
        project.setTrialScope(FileParseHelper.getTrialScope(doc));
        // 3. 受试者信息
        // 3.1 年龄*（不可更新项、公示项）
        project.setAge(FileParseHelper.getAge(doc));
        // 3.2 性别*（不可更新项、公示项）
        project.setGender(FileParseHelper.getGender(doc));
        // 3.3 健康受试者*（不可更新项、公示项）
        project.setHealthyVolunteers(FileParseHelper.getHealthyVolunteers(doc));
        // 3.4 入选标准*（可更新项、公示项）
        project.setInclusionCriteria(JSON.toJSONString(FileParseHelper.getInclusionCriteria(doc)));
        // 3.5 排除标准*（可更新项、公示项）
        project.setExclusionCriteria(JSON.toJSONString(FileParseHelper.getExclusionCriteria(doc)));
        // 4. 试验分组
        // 4.1 试验药*
        project.setTestDrug(JSON.toJSONString(FileParseHelper.getTrialMedication(doc)));
        // 4.1.1 名称（不可更新项、公示项）
        // 4.1.2 生产信息/批号（不可更新项、不公示项）
        // 4.1.3 用法（不可更新项、公示项）
        // 4.2 对照药
        project.setControlDrug(JSON.toJSONString(FileParseHelper.getControlMedication(doc)));
        // 4.2.1 名称（不可更新项、公示项）
        // 4.2.2 生产信息/批号（不可更新项、不公示项）
        // 4.2.3 用法（不可更新项、公示项）
        // 5. 终点指标
        // 5.1 主要终点指标及评价时间*（不可更新项、公示项）
        project.setPrimaryEndpoints(JSON.toJSONString(FileParseHelper.getPrimaryEndpointAndTimingOfAssessment(doc)));
        // 5.2 次要终点指标及评价时间（不可更新项、公示项）
        project.setSecondaryEndpoints(JSON.toJSONString(FileParseHelper.getSecondaryEndpointAndTimingOfAssessment(doc)));
        // 6. 数据安全监查委员会（DMC）*（可更新项、公示项）
        project.setDataMonitoringCommittee(FileParseHelper.getDataMonitoringCommittee(doc));
        // 7. 为受试者购买试验伤害保险*（可更新项、公示项）
        project.setInsuranceForSubjects(FileParseHelper.getInsurancePurchased(doc));
        //（四）研究者信息
        // 1. 主要研究者信息（可更新项、公示项）
        project.setPrincipalInvestigatorInfo(JSON.toJSONString(FileParseHelper.getPrincipalInvestigatorInformation(doc)));
        // ●姓名*
        // ●学位：
        // ●职称：
        // ●电话：
        // ●Email：
        // ●邮政地址
        // ●邮编
        // ●单位名称
        // 2. 各参加机构信息（可更新项、公示项）
        project.setParticipatingInstitutionsInfo(JSON.toJSONString(FileParseHelper.getInformationOnParticipatingInstitutions(doc)));
        // ●机构名称：
        // ●（主要）研究者
        // ●国家或地区：
        // ●省（州）：
        // ●城市
        //（五）伦理委员会信息*（不可更新项、公示项）
        project.setEthicsCommitteeInfo(JSON.toJSONString(FileParseHelper.getEthicsCommitteeInformation(doc)));
        // ●名称：
        // ●审查结论
        // ●伦理批件/备案证明文件
        // ●批准日期/备案日期：
        // ●知情同意书：
        // ●成员姓名：
        // ●IRB/EC

        //（六）试验状态信息
        // 1. 试验状态*（可更新项、公示项）
        project.setTrialStatus(FileParseHelper.getTrialStatus(doc));
        // 2. 试验人数
        ParticipantsNumber participantsNumber = FileParseHelper.getNumberOfParticipants(doc);
        // 2.1 目标入组人数
        project.setTargetEnrollmentDomestic(getTryInteger(participantsNumber.getTargetEnrollmentDomestic()));
        project.setTargetEnrollmentForeign(getTryInteger(participantsNumber.getTargetEnrollmentForeign()));
        // 2.3 已入组人数
        project.setCurrentEnrollmentDomestic(getTryInteger(participantsNumber.getCurrentEnrollmentDomestic()));
        project.setCurrentEnrollmentForeign(getTryInteger(participantsNumber.getCurrentEnrollmentForeign()));
        // 2.5 实际入组总人数
        project.setTotalActualEnrollmentDomestic(getTryInteger(participantsNumber.getTotalActualEnrollmentDomestic()));
        project.setTotalActualEnrollmentForeign(getTryInteger(participantsNumber.getTotalActualEnrollmentForeign()));
        // 3. 受试者招募及试验完成日期
        TrialCompletionDate trialCompletionDate = FileParseHelper.getSubjectRecruitmentAndTrialCompletionDate(doc);
        // 第一例受试者签署知情同意书日期
        project.setFirstConsentDateDomestic(_getTryLocalDateTime(trialCompletionDate.getFirstConsentDateDomestic()));
        project.setFirstConsentDateForeign(_getTryLocalDateTime(trialCompletionDate.getFirstEnrollmentDateForeign()));
        // 第一例受试者入组日期
        project.setFirstEnrollmentDateDomestic(_getTryLocalDateTime(trialCompletionDate.getFirstEnrollmentDateDomestic()));
        project.setFirstEnrollmentDateForeign(_getTryLocalDateTime(trialCompletionDate.getFirstEnrollmentDateForeign()));
        // 试验完成日期
        // 试验暂停日期
        // 试验终止日期
        project.setCompletionDateDomestic(_getTryLocalDateTime(trialCompletionDate.getCompletionDateDomestic()));
        project.setCompletionDateForeign(_getTryLocalDateTime(trialCompletionDate.getCompletionDateForeign()));
        //（八）临床试验结果摘要（不可更新项、公示项）
        project.setClinicalTrialResultsSummary(JSON.toJSONString(FileParseHelper.getTrialResultsSummary(doc)));

        return project;
    }
}
