package com.onework.boot.module.crawler.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author onework
 * @since 2025-05-30
 */
@Getter
@Setter
@TableName("ow_cde_project")
public class CDEProject extends BaseDO {

    /**
     * 唯一值，不重复
     */
    @TableId("uid")
    private String uid;

    /**
     * 首次公示信息日期
     */
    @TableField("first_public_info_date")
    private LocalDateTime firstPublicInfoDate;

    /**
     * 登记号
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 相关登记号
     */
    @TableField("related_registration_number")
    private String relatedRegistrationNumber;

    /**
     * 药物名称
     */
    @TableField("drug_name")
    private String drugName;

    /**
     * 曾用名
     */
    @TableField("former_name")
    private String formerName;

    /**
     * 药物类型
     */
    @TableField("drug_type")
    private String drugType;

    /**
     * 受理号/备案号
     */
    @TableField("acceptance_number")
    private String acceptanceNumber;

    /**
     * 适应症
     */
    @TableField("indication")
    private String indication;

    /**
     * 试验专业题目
     */
    @TableField("professional_title")
    private String professionalTitle;

    /**
     * 试验通俗题目
     */
    @TableField("popular_title")
    private String popularTitle;

    /**
     * 试验方案编号
     */
    @TableField("trial_protocol_number")
    private String trialProtocolNumber;

    /**
     * 方案最新版本号
     */
    @TableField("latest_protocol_version")
    private String latestProtocolVersion;

    /**
     * 版本日期
     */
    @TableField("version_date")
    private String versionDate;

    /**
     * 是否联合用药
     */
    @TableField("combined_medication")
    private String combinedMedication;

    /**
     * 申请人名称
     */
    @TableField("applicant_name")
    private String applicantName;

    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系人座机
     */
    @TableField("contact_landline")
    private String contactLandline;

    /**
     * 联系人手机号
     */
    @TableField("contact_mobile")
    private String contactMobile;

    /**
     * 联系人 Email
     */
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 联系人邮政地址
     */
    @TableField("contact_address")
    private String contactAddress;

    /**
     * 联系人邮编
     */
    @TableField("contact_zip_code")
    private String contactZipCode;

    /**
     * 试验目的
     */
    @TableField("trial_purpose")
    private String trialPurpose;

    /**
     * 试验分类
     */
    @TableField("trial_classification")
    private String trialClassification;

    /**
     * 试验分期
     */
    @TableField("trial_phase")
    private String trialPhase;

    /**
     * 设计类型
     */
    @TableField("design_type")
    private String designType;

    /**
     * 随机化
     */
    @TableField("randomization")
    private String randomization;

    /**
     * 盲法
     */
    @TableField("blinding")
    private String blinding;

    /**
     * 试验范围
     */
    @TableField("trial_scope")
    private String trialScope;

    /**
     * 年龄
     */
    @TableField("age")
    private String age;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 健康受试者
     */
    @TableField("healthy_volunteers")
    private String healthyVolunteers;

    /**
     * 入选标准
     */
    @TableField("inclusion_criteria")
    private String inclusionCriteria;

    /**
     * 排除标准
     */
    @TableField("exclusion_criteria")
    private String exclusionCriteria;

    /**
     * 试验药
     */
    @TableField("test_drug")
    private String testDrug;

    /**
     * 对照药
     */
    @TableField("control_drug")
    private String controlDrug;

    /**
     * 主要终点指标及评价时间
     */
    @TableField("primary_endpoints")
    private String primaryEndpoints;

    /**
     * 次要终点指标及评价时间
     */
    @TableField("secondary_endpoints")
    private String secondaryEndpoints;

    /**
     * 数据安全监查委员会
     */
    @TableField("data_monitoring_committee")
    private String dataMonitoringCommittee;

    /**
     * 为受试者购买试验伤害保险
     */
    @TableField("insurance_for_subjects")
    private String insuranceForSubjects;

    /**
     * 主要研究者信息
     */
    @TableField("principal_investigator_info")
    private String principalInvestigatorInfo;

    /**
     * 伦理委员会信息
     */
    @TableField("ethics_committee_info")
    private String ethicsCommitteeInfo;

    /**
     * 各参加机构信息
     */
    @TableField("participating_institutions_info")
    private String participatingInstitutionsInfo;

    /**
     * 试验状态
     */
    @TableField("trial_status")
    private String trialStatus;

    /**
     * 目标入组人数（国内）
     */
    @TableField("target_enrollment_domestic")
    private Integer targetEnrollmentDomestic;

    /**
     * 目标入组人数（国外）
     */
    @TableField("target_enrollment_foreign")
    private Integer targetEnrollmentForeign;

    /**
     * 已入组人数（国内）
     */
    @TableField("current_enrollment_domestic")
    private Integer currentEnrollmentDomestic;

    /**
     * 已入组人数（国外）
     */
    @TableField("current_enrollment_foreign")
    private Integer currentEnrollmentForeign;

    /**
     * 实际入组总人数（国内）
     */
    @TableField("total_actual_enrollment_domestic")
    private Integer totalActualEnrollmentDomestic;

    /**
     * 实际入组总人数（国外）
     */
    @TableField("total_actual_enrollment_foreign")
    private Integer totalActualEnrollmentForeign;

    /**
     * 第一例受试者签署知情同意书日期（国内）
     */
    @TableField("first_consent_date_domestic")
    private LocalDateTime firstConsentDateDomestic;

    /**
     * 第一例受试者签署知情同意书日期（国外）
     */
    @TableField("first_consent_date_foreign")
    private LocalDateTime firstConsentDateForeign;

    /**
     * 第一例受试者入组日期（国内）
     */
    @TableField("first_enrollment_date_domestic")
    private LocalDateTime firstEnrollmentDateDomestic;

    /**
     * 第一例受试者入组日期（国外）
     */
    @TableField("first_enrollment_date_foreign")
    private LocalDateTime firstEnrollmentDateForeign;

    /**
     * 完成日期（国内）
     */
    @TableField("completion_date_domestic")
    private LocalDateTime completionDateDomestic;

    /**
     * 完成日期（国外）
     */
    @TableField("completion_date_foreign")
    private LocalDateTime completionDateForeign;

    /**
     * 临床试验结果摘要
     */
    @TableField("clinical_trial_results_summary")
    private String clinicalTrialResultsSummary;

    /**
     * 是否分析
     */
    @TableField("is_analysis")
    private Boolean isAnalysis;
}
