package com.onework.boot.scrape.site.ctr;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson2.JSON;
import com.onework.boot.scrape.dal.dataobject.CTRProject;
import com.onework.boot.scrape.site.ctr.dtos.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CTRParseHelper {

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
     * 设置注册号
     */
    private static void setRegistrationNumber(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2)");
        project.setRegistrationNumber(elements.text());
    }

    /**
     * 设置最近更新日期
     */
    private static void setLastRefreshedDate(Document document, CTRProject project) {
        Elements elements = document.select("td[class='splaceTen']");
        project.setLastRefreshedDate(_getTryLocalDateTime(elements.text()));
    }

    /**
     * 设置注册时间
     */
    private static void setRegistrationDate(Document document, CTRProject project) {
        Elements elements = document.select("td[class='splaceTen1']");
        project.setRegistrationDate(_getTryLocalDateTime(elements.text()));
    }

    /**
     * 设置注册状态
     */
    private static void setRegistrationStatus(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p");
        project.setRegistrationStatus(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(5) > td:nth-of-type(2) > p");
        project.setRegistrationStatusEn(enElements.text());
    }

    /**
     * 设置注册题目
     */
    private static void setPublicTitle(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(6) > td:nth-of-type(2)");
        project.setPublicTitle(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(7) > td:nth-of-type(2) > p");
        project.setPublicTitleEn(enElements.text());
    }

    /**
     * 设置注册题目简写
     */
    private static void setEnglishAcronym(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(8) > td:nth-of-type(2)");
        project.setEnglishAcronym(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(9) > td:nth-of-type(2)");
        project.setEnglishAcronymEn(enElements.text());
    }

    /**
     * 设置研究课题的正式科学名称
     */
    private static void setScientificTitle(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(10) > td:nth-of-type(2) > p");
        project.setScientificTitle(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(11) > td:nth-of-type(2) > p");
        project.setScientificTitleEn(enElements.text());
    }

    /**
     * 设置研究课题代号(代码)
     */
    private static void setStudySubjectId(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(3) > table > tbody > tr:nth-of-type(12) > td:nth-of-type(2)");
        project.setStudySubjectId(elements.text());
    }

    /**
     * 设置申请注册联系人
     */
    private static void setApplicant(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p");
        project.setApplicant(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setApplicantEn(enElements.text());
    }

    /**
     * 设置研究负责人
     */
    private static void setStudyLeader(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(4) > p");
        project.setStudyLeader(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(4)");
        project.setStudyLeaderEn(enElements.text());
    }

    /**
     * 设置申请注册联系人电话
     */
    private static void setApplicantPhone(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(2)");
        project.setApplicantPhone(elements.text());
    }

    /**
     * 设置研究负责人电话
     */
    private static void setStudyLeaderPhone(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(4)");
        project.setStudyLeaderPhone(elements.text());
    }

    /**
     * 设置申请注册联系人传真
     */
    private static void setApplicantFax(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(4) > td:nth-of-type(2)");
        project.setApplicantFax(elements.text());

    }

    /**
     * 设置研究负责人传真
     */
    private static void setStudyLeaderFax(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(4) > td:nth-of-type(4)");
        project.setStudyLeaderFax(elements.text());
    }

    /**
     * 设置申请注册联系人电子邮件
     */
    private static void setApplicantEmail(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(5) > td:nth-of-type(2)");
        project.setApplicantEmail(elements.text());

    }

    /**
     * 设置研究负责人电子邮件
     */
    private static void setStudyLeaderEmail(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(5) > td:nth-of-type(4)");
        project.setStudyLeaderEmail(elements.text());
    }

    /**
     * 设置申请单位网址(自愿提供)
     */
    private static void setApplicantWebsite(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(6) > td:nth-of-type(2)");
        project.setApplicantWebsite(elements.text());
    }

    /**
     * 设置研究负责人网址(自愿提供)
     */
    private static void setStudyLeaderWebsite(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(6) > td:nth-of-type(4)");
        project.setStudyLeaderWebsite(elements.text());
    }

    /**
     * 设置申请注册联系人通讯地址
     */
    private static void setApplicantAddress(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(7) > td:nth-of-type(2) > p");
        project.setApplicantAddress(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(8) > td:nth-of-type(2) > p");
        project.setApplicantAddressEn(enElements.text());
    }

    /**
     * 设置研究负责人通讯地址
     */
    private static void setStudyLeaderAddress(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(7) > td:nth-of-type(4) > p");
        project.setStudyLeaderAddress(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(8) > td:nth-of-type(4) > p");
        project.setStudyLeaderAddressEn(enElements.text());
    }

    /**
     * 设置申请注册联系人邮政编码
     */
    private static void setApplicantPostcode(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(9) > td:nth-of-type(2)");
        project.setApplicantPostcode(elements.text());

    }

    /**
     * 设置研究负责人邮政编码
     */
    private static void setStudyLeaderPostcode(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(9) > td:nth-of-type(4)");
        project.setStudyLeaderPostcode(elements.text());
    }

    /**
     * 设置申请人所在单位
     */
    private static void setApplicantInstitution(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(10) > td:nth-of-type(2) > p");
        project.setApplicantInstitution(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(11) > td:nth-of-type(2) > p");
        project.setApplicantInstitutionEn(enElements.text());
    }

    /**
     * 设置研究负责人所在单位
     */
    private static void setLeaderAffiliation(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(12) > td:nth-of-type(2) > p");
        project.setLeaderAffiliation(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(4) > table > tbody > tr:nth-of-type(13) > td:nth-of-type(2) > p");
        project.setLeaderAffiliationEn(enElements.text());
    }

    /**
     * 设置是否获伦理委员会批准
     */
    private static void setEthicCommitteeApproved(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(1) > tr:nth-of-type(1) > td:nth-of-type(2) > p");
        project.setEthicCommitteeApproved(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(1) > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setEthicCommitteeApprovedEn(enElements.text());
    }

    /**
     * 设置伦理委员会批件文号
     */
    private static void setEthicCommitteeNo(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(1) > td:nth-of-type(2)");
        project.setEthicCommitteeNo(elements.text());

    }

    /**
     * 设置伦理委员会批件附件
     */
    private static void setEthicCommitteeFile(Document document, CTRProject project) {

        Elements elements = document.select("a[class='getview']");
        project.setEthicCommitteeFile(elements.text());

    }

    /**
     * 设置批准本研究的伦理委员会名称
     */
    private static void setEthicCommitteeName(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setEthicCommitteeName(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(3) > td:nth-of-type(2) > p");
        project.setEthicCommitteeNameEn(enElements.text());
    }

    /**
     * 设置伦理委员会批准日期
     */
    private static void setEthicCommitteeApprovalDate(Document document, CTRProject project) {

        Elements elements = document.select("td[class='splaceTen2']");
        project.setEthicCommitteeApprovalDate(_getTryLocalDateTime(elements.text()));
    }

    /**
     * 设置伦理委员会联系人
     */
    private static void setEthicCommitteeContactName(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(5) > td:nth-of-type(2) > p");
        project.setEthicCommitteeContactName(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(6) > td:nth-of-type(2) > p");
        project.setEthicCommitteeContactNameEn(enElements.text());
    }

    /**
     * 设置伦理委员会联系地址
     */
    private static void setEthicCommitteeContactAddress(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(7) > td:nth-of-type(2) > p");
        project.setEthicCommitteeContactAddress(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(8) > td:nth-of-type(2) > p");
        project.setEthicCommitteeContactAddressEn(enElements.text());
    }

    /**
     * 设置伦理委员会联系人电话
     */
    private static void setEthicCommitteeContactPhone(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(9) > td:nth-of-type(2)");
        project.setEthicCommitteeContactPhone(elements.text());

    }

    /**
     * 设置伦理委员会联系人邮箱
     */
    private static void setEthicCommitteeContactEmail(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(5) > table > tbody:nth-of-type(2) > tr:nth-of-type(9) > td:nth-of-type(4)");
        project.setEthicCommitteeContactEmail(elements.text());

    }

    /**
     * 设置研究实施负责（组长）单位
     */
    private static void setPrimarySponsor(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p");
        project.setPrimarySponsor(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setPrimarySponsorEn(enElements.text());
    }

    /**
     * 设置研究实施负责（组长）单位地址
     */
    private static void setPrimarySponsorAddress(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(3) > td:nth-of-type(2)");
        project.setPrimarySponsorAddress(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p");
        project.setPrimarySponsorAddressEn(enElements.text());
    }

    /**
     * 设置试验主办单位
     */
    private static void setSecondarySponsor(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(5) > td:nth-of-type(2) > table > tbody");
        CTRSecondarySponsor CTRSecondarySponsor = new CTRSecondarySponsor();
        String country = elements.select("tr:nth-of-type(1) > td:nth-of-type(2) > p").text();
        String countryEn = elements.select("tr:nth-of-type(2) > td:nth-of-type(2) > p").text();
        CTRSecondarySponsor.setCountry(country);
        CTRSecondarySponsor.setCountry(countryEn);

        String province = elements.select(" tr:nth-of-type(1) > td:nth-of-type(4)").text();
        String provinceEn = elements.select("tr:nth-of-type(2) > td:nth-of-type(4) > p").text();
        CTRSecondarySponsor.setProvince(province);
        CTRSecondarySponsor.setProvinceEn(provinceEn);

        String city = elements.select("r:nth-of-type(1) > td:nth-of-type(2) > p").text();
        String cityEn = elements.select("r:nth-of-type(1) > td:nth-of-type(2) > p").text();
        CTRSecondarySponsor.setCity(city);
        CTRSecondarySponsor.setCityEn(cityEn);

        String institutionHospital = elements.select(" tr:nth-of-type(3) > td:nth-of-type(2) > p").text();
        String institutionHospitalEn = elements.select(" tr:nth-of-type(4) > td:nth-of-type(2) > p").text();
        CTRSecondarySponsor.setInstitutionHospital(institutionHospital);
        CTRSecondarySponsor.setInstitutionHospitalEn(institutionHospitalEn);

        String address = elements.select("tr:nth-of-type(3) > td:nth-of-type(4) > p").text();
        String addressEn = elements.select("tr:nth-of-type(4) > td:nth-of-type(4) > p").text();
        CTRSecondarySponsor.setAddress(address);
        CTRSecondarySponsor.setCountryEn(addressEn);

        project.setSecondarySponsor(JSON.toJSONString(CTRSecondarySponsor));
    }

    /**
     * 设置经费或物资来源
     */
    private static void setFundingSource(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(6) > td:nth-of-type(2) > p");
        project.setFundingSource(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(7) > td:nth-of-type(2) > p");
        project.setFundingSourceEn(enElements.text());
    }

    /**
     * 设置研究疾病
     */
    private static void setTargetDisease(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(8) > td:nth-of-type(2) > p");
        project.setTargetDisease(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(9) > td:nth-of-type(2) > p");
        project.setTargetDiseaseEn(enElements.text());
    }

    /**
     * 设置研究疾病代码
     */
    private static void setTargetDiseaseCode(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(10) > td:nth-of-type(2)");
        project.setTargetDiseaseCode(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(11) > td:nth-of-type(2)");
        project.setTargetDiseaseCodeEn(enElements.text());
    }

    /**
     * 设置研究类型
     */
    private static void setStudyType(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(12) > td:nth-of-type(2) > p");
        project.setStudyType(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(13) > td:nth-of-type(2) > p");
        project.setStudyTypeEn(enElements.text());
    }

    /**
     * 设置研究所处阶段
     */
    private static void setStudyPhase(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(14) > td:nth-of-type(2)");
        project.setStudyPhase(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(15) > td:nth-of-type(2) > p");
        project.setStudyPhaseEn(enElements.text());
    }

    /**
     * 设置研究设计
     */
    private static void setStudyDesign(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(16) > td:nth-of-type(2) > p");
        project.setStudyDesign(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(17) > td:nth-of-type(2) > p");
        project.setStudyDesignEn(enElements.text());
    }

    /**
     * 设置研究目的
     */
    private static void setStudyObjectives(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(18) > td:nth-of-type(2) > p");
        project.setStudyObjectives(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(19) > td:nth-of-type(2) > p");
        project.setStudyObjectivesEn(enElements.text());
    }

    /**
     * 设置物成份或治疗方案详述
     */
    private static void setTreatmentDescription(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(20) > td:nth-of-type(2) > p");
        project.setTreatmentDescription(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(21) > td:nth-of-type(2)");
        project.setTreatmentDescriptionEn(enElements.text());
    }

    /**
     * 设置纳入标准
     */
    private static void setInclusionCriteria(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(22) > td:nth-of-type(2) > p");
        project.setInclusionCriteria(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(23) > td:nth-of-type(2) > p");
        project.setInclusionCriteriaEn(enElements.text());
    }

    /**
     * 设置排除标准
     */
    private static void setExclusionCriteria(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(24) > td:nth-of-type(2) > p");
        project.setExclusionCriteria(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(6) > table:nth-of-type(1) > tbody > tr:nth-of-type(25) > td:nth-of-type(2) > p");
        project.setExclusionCriteriaEn(enElements.text());
    }

    /**
     * 设置研究实施时间
     */
    private static void setStudyExecutionTime(Document document, CTRProject project) {
        Elements startElements = document.select("span[class='splaceTen3']");
        project.setStudyExecutionStartTime(_getTryLocalDateTime(startElements.text()));

        Elements endElements = document.select("span[class='splaceTen4']");
        project.setStudyExecutionEndTime(_getTryLocalDateTime(endElements.text()));
    }

    /**
     * 设置征募观察对象时间
     */
    private static void setRecruitingTime(Document document, CTRProject project) {
        Elements startElements = document.select("span[class='splaceTen5']");
        project.setRecruitingStartTime(_getTryLocalDateTime(startElements.text()));

        Elements endElements = document.select("span[class='splaceTen6']");
        project.setRecruitingEndTime(_getTryLocalDateTime(endElements.text()));
    }

    /**
     * 设置干预措施
     */
    private static void setInterventions(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(7) > table > tbody > tr > td:nth-of-type(2) > table");
        List<CTRIntervention> CTRInterventions = new ArrayList<>();
        for (Element tableElement : elements) {
            CTRIntervention CTRIntervention = new CTRIntervention();
            CTRIntervention.setGroup(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p").text());
            CTRIntervention.setGroupEn(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2)").text());
            CTRIntervention.setSampleSize(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(4)").text());
            CTRIntervention.setIntervention(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2) > p").text());
            CTRIntervention.setInterventionEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p").text());
            CTRIntervention.setInterventionCode(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(4)").text());
            CTRInterventions.add(CTRIntervention);
        }
        project.setInterventions(JSON.toJSONString(CTRInterventions));
    }

    /**
     * 设置研究实施地点
     */
    private static void setRecruitmentLocations(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(8) > table > tbody > tr > td:nth-of-type(2) > table");
        List<CTRRecruitmentLocation> CTRRecruitmentLocations = new ArrayList<>();
        for (Element tableElement : elements) {
            CTRRecruitmentLocation CTRRecruitmentLocation = new CTRRecruitmentLocation();
            CTRRecruitmentLocation.setCountry(tableElement.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(2)").text());
            CTRRecruitmentLocation.setCountry(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p").text());
            CTRRecruitmentLocation.setProvince(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(4)").text());
            CTRRecruitmentLocation.setProvinceEn(tableElement.select(" tbody > tr:nth-of-type(2) > td:nth-of-type(4) > p").text());
            CTRRecruitmentLocation.setCity(tableElement.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(6)").text());
            CTRRecruitmentLocation.setCityEn(tableElement.select(" table > tbody > tr:nth-of-type(2) > td:nth-of-type(6) > p").text());
            CTRRecruitmentLocation.setLevel(tableElement.select(" tbody > tr:nth-of-type(3) > td:nth-of-type(4)").text());
            CTRRecruitmentLocation.setLevelEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(4) > p:nth-of-type(1)").text());
            CTRRecruitmentLocation.setInstitutionHospital(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2)").text());
            CTRRecruitmentLocation.setInstitutionHospitalEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p").text());
            CTRRecruitmentLocations.add(CTRRecruitmentLocation);
        }
        project.setRecruitmentLocations(JSON.toJSONString(CTRRecruitmentLocations));
    }

    /**
     * 设置测量指标
     */
    private static void setOutcomes(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(9) > table > tbody > tr > td:nth-of-type(2) > div > table");
        List<CTROutcome> CTROutcomes = new ArrayList<>();
        for (Element tableElement : elements) {
            CTROutcome CTROutcome = new CTROutcome();
            CTROutcome.setOutcome(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p").text());
            CTROutcome.setOutcomeEn(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p").text());
            CTROutcome.setType(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(4) > p").text());
            CTROutcome.setTypeEn(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(4) > p").text());
            CTROutcome.setMeasureTimePoint(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2) > p").text());
            CTROutcome.setMeasureTimePointEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p").text());
            CTROutcome.setMeasureMethod(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(4) > p").text());
            CTROutcome.setMeasureMethodEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(4) > p").text());
            CTROutcomes.add(CTROutcome);
        }
        project.setOutcomes(JSON.toJSONString(CTROutcomes));
    }

    /**
     * 设置采集人体标本
     */
    private static void setSampleCollection(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(9) > table > tbody > tr > td:nth-of-type(2) > div > table");
        List<CTRSample> CTRSamples = new ArrayList<>();
        for (Element tableElement : elements) {
            CTRSample CTRSample = new CTRSample();
            CTRSample.setSampleName(tableElement.select(" tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p").text());
            CTRSample.setSampleNameEn(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p").text());
            CTRSample.setTissue(tableElement.select("tbody > tr:nth-of-type(1) > td:nth-of-type(4)").text());
            CTRSample.setTissueEn(tableElement.select("tbody > tr:nth-of-type(2) > td:nth-of-type(4)").text());
            CTRSample.setFateOfSample(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(2)").text());
            CTRSample.setFateOfSampleEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p").text());
            CTRSample.setNote(tableElement.select("tbody > tr:nth-of-type(3) > td:nth-of-type(4)").text());
            CTRSample.setNoteEn(tableElement.select("tbody > tr:nth-of-type(4) > td:nth-of-type(4)").text());
            CTRSamples.add(CTRSample);
        }
        project.setSampleCollection(JSON.toJSONString(CTRSamples));
    }

    /**
     * 设置征募研究对象情况
     */
    private static void setRecruitingStatus(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p:nth-of-type(1)");
        project.setRecruitingStatus(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p:nth-of-type(2)");
        project.setRecruitingStatusEn(enElements.text());
    }

    /**
     * 设置年龄范围
     */
    private static void setParticipantAge(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(4) > table > tbody");
        project.setParticipantAge(elements.text());
    }

    /**
     * 设置性别
     */
    private static void setGender(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setGender(elements.text());
    }

    /**
     * 设置随机方法
     */
    private static void setRandomizationMethod(Document document, CTRProject project) {

        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(2) > p");
        project.setRandomizationMethod(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(11) > table > tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p");
        project.setRandomizationMethodEn(enElements.text());
    }

    /**
     * 设置是否公开试验完成后的统计结果
     */
    private static void setResultPublication(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(12) > table > tbody > tr > td:nth-of-type(2) > p");
        project.setResultPublication(elements.text());
    }

    /**
     * 设置盲法
     */
    private static void setBlinding(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(13) > table > tbody:nth-of-type(1) > tr:nth-of-type(1) > td:nth-of-type(2) > p");
        project.setBlinding(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(13) > table > tbody:nth-of-type(1) > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setBlindingEn(enElements.text());
    }

    /**
     * 设置试验完成后的统计结果
     */
    private static void setFinalResults(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(13) > table > tbody:nth-of-type(1) > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setFinalResults(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(13) > table > tbody:nth-of-type(2) > tr:nth-of-type(2) > td:nth-of-type(2) > p > label > a");
        project.setFinalResultsEn(enElements.text());
    }

    /**
     * 设置是否共享原始数据
     */
    private static void setIpdSharing(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(1) > td:nth-of-type(2) > p");
        project.setIpdSharing(elements.text());
    }

    /**
     * 设置共享原始数据的方式
     */
    private static void setIpdSharingMethod(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(2) > td:nth-of-type(2) > p");
        project.setIpdSharingMethod(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(3) > td:nth-of-type(2) > p");
        project.setIpdSharingMethodEn(enElements.text());
    }

    /**
     * 设置数据采集和管理
     */
    private static void setDataManagement(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(4) > td:nth-of-type(2) > p");
        project.setDataManagement(elements.text());

        Elements enElements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(5) > td:nth-of-type(2) > p");
        project.setDataManagementEn(enElements.text());
    }

    /**
     * 设置数据与安全监察委员会
     */
    private static void setSafetyCommittee(Document document, CTRProject project) {
        Elements elements = document.select("html > body > div:nth-of-type(1) > div > div:nth-of-type(3) > div:nth-of-type(14) > table > tbody > tr:nth-of-type(6) > td:nth-of-type(2) > p");
        project.setSafetyCommittee(elements.text().split("/")[0]);
        if (elements.text().split("/").length > 1) {
            project.setSafetyCommitteeEn(elements.text().split("/")[1]);
        }

    }

    public static CTRProject createProject(Document document) {
        CTRProject project = new CTRProject();
        // Registration number  注册号
        setRegistrationNumber(document, project);
//        Date of Last Refreshed 最近更新日期
        setLastRefreshedDate(document, project);
//        Date of Registration 注册时间
        setRegistrationDate(document, project);
//        Registration Status 注册号状态
        setRegistrationStatus(document, project);
//        Public title 注册题目
        setPublicTitle(document, project);
//        English Acronym 注册题目简写
        setEnglishAcronym(document, project);
//        Scientific title 研究课题的正式科学名称
        setScientificTitle(document, project);
//        Study subject ID 研究课题代号(代码)
        setStudySubjectId(document, project);
//                Applicant 申请注册联系人
        setApplicant(document, project);
//        Study leader 研究负责人
        setStudyLeader(document, project);
//        Applicant telephone 申请注册联系人电话
        setApplicantPhone(document, project);
//        Study leader's telephone 研究负责人电话
        setStudyLeaderPhone(document, project);
//        Applicant Fax 申请注册联系人传真
        setApplicantFax(document, project);
//        Study leader's fax 研究负责人传真
        setStudyLeaderFax(document, project);
//        Applicant E-mail 申请注册联系人电子邮件
        setApplicantEmail(document, project);
//        Study leader's E-mail 研究负责人电子邮件
        setStudyLeaderEmail(document, project);
//        Applicant website(voluntary supply) 申请单位网址(自愿提供)
        setApplicantWebsite(document, project);
//        Study leader's website(voluntary supply) 研究负责人网址(自愿提供)
        setStudyLeaderWebsite(document, project);
//        Applicant address 申请注册联系人通讯地址
        setApplicantAddress(document, project);
//        Study leader's address 研究负责人通讯地址
        setStudyLeaderAddress(document, project);
//        Applicant postcode 申请注册联系人邮政编码
        setApplicantPostcode(document, project);
//        Study leader's postcode 研究负责人邮政编码
        setStudyLeaderPostcode(document, project);
//        Applicant's institution 申请人所在单位
        setApplicantInstitution(document, project);
//        Affiliation of the Leader 研究负责人所在单位
        setLeaderAffiliation(document, project);
//        Approved by ethic committee 是否获伦理委员会批准
        setEthicCommitteeApproved(document, project);
//        Approved No. of ethic committee 伦理委员会批件文号
        setEthicCommitteeNo(document, project);
//        Approved file of Ethical Committee 伦理委员会批件附件
        setEthicCommitteeFile(document, project);
//        Name of the ethic committee 批准本研究的伦理委员会名称
        setEthicCommitteeName(document, project);
//        Date of approved by ethic committee 伦理委员会批准日期
        setEthicCommitteeApprovalDate(document, project);
//        Contact Name of the ethic committee 伦理委员会联系人
        setEthicCommitteeContactName(document, project);
//        Contact Address of the ethic committee 伦理委员会联系地址
        setEthicCommitteeContactAddress(document, project);
//        Contact phone of the ethic committee 伦理委员会联系人电话
        setEthicCommitteeContactPhone(document, project);
//        Contact email of the ethic committee 伦理委员会联系人邮箱
        setEthicCommitteeContactEmail(document, project);
//        Primary sponsor 研究实施负责（组长）单位
        setPrimarySponsor(document, project);
//        Primary sponsor's address 研究实施负责（组长）单位地址
        setPrimarySponsorAddress(document, project);
//        Secondary sponsor 试验主办单位
        setSecondarySponsor(document, project);
//        Source(s) of funding 经费或物资来源
        setFundingSource(document, project);
//        arget disease 研究疾病
        setTargetDisease(document, project);
//        Target disease code 研究疾病代码
        setTargetDiseaseCode(document, project);
//        Study type 研究类型
        setStudyType(document, project);
//        Study phase 研究所处阶段
        setStudyPhase(document, project);
//        tudy design 研究设计
        setStudyDesign(document, project);
//        Objectives of Study 研究目的
        setStudyObjectives(document, project);
//        Description for medicine or protocol of treatment in detail 物成份或治疗方案详述
        setTreatmentDescription(document, project);
//        Inclusion criteria 纳入标准
        setInclusionCriteria(document, project);
//        Exclusion criteria 排除标准
        setExclusionCriteria(document, project);
//        Study execute time 研究实施时间
        setStudyExecutionTime(document, project);
//        Recruiting time 征募观察对象时间
        setRecruitingTime(document, project);
//        Interventions 干预措施
        setInterventions(document, project);
//        Countries of recruitment and research settings 研究实施地点
        setRecruitmentLocations(document, project);
//        Outcomes 测量指标
        setOutcomes(document, project);
//        Collecting sample(s)from participants 采集人体标本
        setSampleCollection(document, project);
//        Recruiting status 征募研究对象情况
        setRecruitingStatus(document, project);
//        Participant age 年龄范围
        setParticipantAge(document, project);
//        Gender 性别
        setGender(document, project);
//        Randomization Procedure 随机方法
        setRandomizationMethod(document, project);
//        Calculated Results after the Study Completed public access 是否公开试验完成后的统计结果
        setResultPublication(document, project);
//        Blinding 盲法
        setBlinding(document, project);
//        Calculated Results after the Study Completed 试验完成后的统计结果
        setFinalResults(document, project);
//        IPD sharing 是否共享原始数据
        setIpdSharing(document, project);
//        The way of sharing IPD 共享原始数据的方式
        setIpdSharingMethod(document, project);
//        Data collection and Management 数据采集和管理
        setDataManagement(document, project);
//        Data and Safety Monitoring Committee 数据与安全监察委员会
        setSafetyCommittee(document, project);
        return project;
    }
}
