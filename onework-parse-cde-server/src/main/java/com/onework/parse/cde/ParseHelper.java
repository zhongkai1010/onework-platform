package com.onework.parse.cde;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHelper {

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
        return elements.text();
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
        AtomicInteger index = new AtomicInteger(1);

        elements.forEach(element -> items.add(index.getAndIncrement() + "、" + element.select("td:nth-of-type(2)").text()));
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
        AtomicInteger index = new AtomicInteger(1);
        elements.forEach(element -> items.add(index.getAndIncrement() + "、" + element.select("td:nth-of-type(2)").text()));
        return items;
    }

    /**
     * 试验药
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getTrialMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                items.add(String.format("%s、%s | %s", number.text(), name.text(), usage.text()));
            }
        });
        return items;
    }

    /**
     * 对照药
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getControlMedication(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(5) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements name = element.select("td:nth-of-type(2)");
            Elements usage = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                items.add(String.format("%s、%s | %s", number.text(), name.text(), usage.text()));
            }
        });
        return items;
    }

    /**
     * 主要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getPrimaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(1) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                items.add(String.format("%s 、%s | %s | %s", number.text(), indicator.text(), evaluationTime.text(), primaryEndpoint.text()));
            }
        });
        return items;
    }

    /**
     * 次要终点指标及评价时间
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getSecondaryEndpointAndTimingOfAssessment(Document doc) {
        Elements elements = doc.select("html > body > main > div:nth-of-type(2) > div > div > div > div > div:nth-of-type(5) > div > div:nth-of-type(2) > div:nth-of-type(2) > div > table:nth-of-type(6) > tbody > tr:nth-of-type(2) > td > table > tbody > tr");
        List<String> items = new ArrayList<>();
        elements.forEach(element -> {
            Elements number = element.select("td:nth-of-type(1)");
            Elements indicator = element.select("td:nth-of-type(2)");
            Elements evaluationTime = element.select("td:nth-of-type(3)");
            Elements primaryEndpoint = element.select("td:nth-of-type(3)");
            if (!number.isEmpty()) {
                items.add(String.format("%s 、%s | %s | %s", number.text(), indicator.text(), evaluationTime.text(), primaryEndpoint.text()));
            }
        });
        return items;
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
    public static List<String> getPrincipalInvestigatorInformation(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*1、主要研究者信息\\s*</div>(.*?)<div class=\"sDPTit2\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            Elements elements = document.select("table");
            elements.forEach(element -> {
                String number = element.select("tbody > tr:nth-of-type(1) > th:nth-of-type(1)").text();
                String name = element.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(1)").text();
                String degree = element.select("tbody > tr:nth-of-type(1) > td:nth-of-type(2)").text();
                String phone = element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(1)").text();
                String email = element.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2)").text();
                String postalAddress = element.select(" tbody > tr:nth-of-type(2) > td:nth-of-type(3)").text();
                String postalCode = element.select(" tbody > tr:nth-of-type(3) > td:nth-of-type(1)").text();
                String organizationName = element.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2)").text();
                items.add(String.format("%s、%s | %s | %s | %s | %s | %s | %s", number, name, degree, phone, email, postalAddress, postalCode, organizationName));
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 各参加机构信息
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getInformationOnParticipatingInstitutions(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、各参加机构信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    String number = element.select("td:nth-of-type(1)").text();
                    String organizationName = element.select("td:nth-of-type(2)").text();
                    String principal = element.select("td:nth-of-type(3)").text();
                    String region = element.select("td:nth-of-type(4)").text();
                    String province = element.select("td:nth-of-type(5)").text();
                    String city = element.select("td:nth-of-type(6)").text();
                    items.add(String.format("%s、%s | %s | %s | %s | %s", number, organizationName, principal, region, province, city));

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
    public static List<String> getEthicsCommitteeInformation(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*五、伦理委员会信息\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    String number = element.select("td:nth-of-type(1)").text();
                    String name = element.select("td:nth-of-type(2)").text();
                    String reviewConclusion = element.select("td:nth-of-type(3)").text();
                    String approvalDate = element.select("td:nth-of-type(4)").text();
                    items.add(String.format("%s、%s | %s | %s", number, name, reviewConclusion, approvalDate));
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

    /**
     *  试验人数
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getNumberOfParticipants(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*2、试验人数\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            String targetEnrollmentNumber = document.select("tr:nth-of-type(1) > td").text();
            String numberEnrolled = document.select("tr:nth-of-type(2) > td").text();
            String totalNumberOfParticipantsEnrolled = document.select("tr:nth-of-type(3) > td").text();
            items.add("目标入组人数:" + targetEnrollmentNumber);
            items.add("已入组人数:" + numberEnrolled);
            items.add("实际入组总人数:" + totalNumberOfParticipantsEnrolled);
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     *  试验日期
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getSubjectRecruitmentAndTrialCompletionDate(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"sDPTit2\">\\s*3、受试者招募及试验完成日期\\s*</div>(.*?)<div", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            String dateOfFirstSubjectsInformedConsent = document.select("tr:nth-of-type(1) > td").text();
            String dateOfFirstSubjectEnrolled = document.select("tr:nth-of-type(2) > td").text();
            items.add("第一例受试者签署知情同意书日期:" + dateOfFirstSubjectsInformedConsent);
            items.add("第一例受试者入组日期:" + dateOfFirstSubjectEnrolled);
            String status = document.select("tr:nth-of-type(3) > th").text();
            String date = document.select("tr:nth-of-type(3) > td").text();
            items.add(String.format("%s:%s", status, date));
            return items;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 临床试验结果摘要
     * @param doc 文档
     * @return 结果
     */
    public static List<String> getTrialResultsSummary(Document doc) {
        Pattern pattern = Pattern.compile("<div class=\"searchDetailPartTit\">\\s*七、临床试验结果摘要\\s*</div>(.*?)<div class=\"searchDetailPartTit\">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(doc.html());
        // 查找匹配项
        if (matcher.find()) {
            String matcherHtml = matcher.group(1).trim();
            Document document = Jsoup.parse(matcherHtml);
            List<String> items = new ArrayList<>();
            Elements elements = document.select("tr");
            elements.forEach(element -> {
                if (!element.select("td:nth-of-type(1)").isEmpty()) {
                    String number = element.select("td:nth-of-type(1)").text();
                    String versionNumber = element.select("td:nth-of-type(2)").text();
                    String versionDate = element.select("td:nth-of-type(3)").text();
                    items.add(String.format("%s、%s | %s", number, versionNumber, versionDate));
                }
            });
            return items;
        } else {
            return new ArrayList<>();
        }
    }
}
