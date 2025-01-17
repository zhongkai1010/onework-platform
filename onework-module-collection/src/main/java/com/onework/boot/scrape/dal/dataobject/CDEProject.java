package com.onework.boot.scrape.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Getter
@Setter
@TableName("ow_cde_project")
@Schema(name = "CDEProject", description = "")
public class CDEProject extends BaseDO {

    @Schema(description = "唯一值，不重复")
    @TableId("uid")
    private String uid;

    @Schema(description = "首次公示信息日期")
    @TableField("first_public_info_date")
    private LocalDateTime firstPublicInfoDate;

    @Schema(description = "登记号")
    @TableField("registration_number")
    private String registrationNumber;

    @Schema(description = "相关登记号")
    @TableField("related_registration_number")
    private String relatedRegistrationNumber;

    @Schema(description = "药物名称")
    @TableField("drug_name")
    private String drugName;

    @Schema(description = "曾用名")
    @TableField("former_name")
    private String formerName;

    @Schema(description = "药物类型")
    @TableField("drug_type")
    private String drugType;

    @Schema(description = "受理号/备案号")
    @TableField("acceptance_number")
    private String acceptanceNumber;

    @Schema(description = "适应症")
    @TableField("indication")
    private String indication;

    @Schema(description = "试验专业题目")
    @TableField("professional_title")
    private String professionalTitle;

    @Schema(description = "试验通俗题目")
    @TableField("popular_title")
    private String popularTitle;

    @Schema(description = "试验方案编号")
    @TableField("trial_protocol_number")
    private String trialProtocolNumber;

    @Schema(description = "方案最新版本号")
    @TableField("latest_protocol_version")
    private String latestProtocolVersion;

    @Schema(description = "版本日期")
    @TableField("version_date")
    private String versionDate;

    @Schema(description = "是否联合用药")
    @TableField("combined_medication")
    private String combinedMedication;

    @Schema(description = "申请人名称")
    @TableField("applicant_name")
    private String applicantName;

    @Schema(description = "联系人姓名")
    @TableField("contact_name")
    private String contactName;

    @Schema(description = "联系人座机")
    @TableField("contact_landline")
    private String contactLandline;

    @Schema(description = "联系人手机号")
    @TableField("contact_mobile")
    private String contactMobile;

    @Schema(description = "联系人 Email")
    @TableField("contact_email")
    private String contactEmail;

    @Schema(description = "联系人邮政地址")
    @TableField("contact_address")
    private String contactAddress;

    @Schema(description = "联系人邮编")
    @TableField("contact_zip_code")
    private String contactZipCode;

    @Schema(description = "试验目的")
    @TableField("trial_purpose")
    private String trialPurpose;

    @Schema(description = "试验分类")
    @TableField("trial_classification")
    private String trialClassification;

    @Schema(description = "试验分期")
    @TableField("trial_phase")
    private String trialPhase;

    @Schema(description = "设计类型")
    @TableField("design_type")
    private String designType;

    @Schema(description = "随机化")
    @TableField("randomization")
    private String randomization;

    @Schema(description = "盲法")
    @TableField("blinding")
    private String blinding;

    @Schema(description = "试验范围")
    @TableField("trial_scope")
    private String trialScope;

    @Schema(description = "年龄")
    @TableField("age")
    private String age;

    @Schema(description = "性别")
    @TableField("gender")
    private String gender;

    @Schema(description = "健康受试者")
    @TableField("healthy_volunteers")
    private String healthyVolunteers;

    @Schema(description = "入选标准")
    @TableField("inclusion_criteria")
    private String inclusionCriteria;

    @Schema(description = "排除标准")
    @TableField("exclusion_criteria")
    private String exclusionCriteria;

    @Schema(description = "试验药")
    @TableField("test_drug")
    private String testDrug;

    @Schema(description = "对照药")
    @TableField("control_drug")
    private String controlDrug;

    @Schema(description = "主要终点指标及评价时间")
    @TableField("primary_endpoints")
    private String primaryEndpoints;

    @Schema(description = "次要终点指标及评价时间")
    @TableField("secondary_endpoints")
    private String secondaryEndpoints;

    @Schema(description = "数据安全监查委员会")
    @TableField("data_monitoring_committee")
    private String dataMonitoringCommittee;

    @Schema(description = "为受试者购买试验伤害保险")
    @TableField("insurance_for_subjects")
    private String insuranceForSubjects;

    @Schema(description = "主要研究者信息")
    @TableField("principal_investigator_info")
    private String principalInvestigatorInfo;

    @Schema(description = "伦理委员会信息")
    @TableField("ethics_committee_info")
    private String ethicsCommitteeInfo;

    @Schema(description = "各参加机构信息")
    @TableField("participating_institutions_info")
    private String participatingInstitutionsInfo;

    @Schema(description = "试验状态")
    @TableField("trial_status")
    private String trialStatus;

    @Schema(description = "目标入组人数（国内）")
    @TableField("target_enrollment_domestic")
    private Integer targetEnrollmentDomestic;

    @Schema(description = "目标入组人数（国外）")
    @TableField("target_enrollment_foreign")
    private Integer targetEnrollmentForeign;

    @Schema(description = "已入组人数（国内）")
    @TableField("current_enrollment_domestic")
    private Integer currentEnrollmentDomestic;

    @Schema(description = "已入组人数（国外）")
    @TableField("current_enrollment_foreign")
    private Integer currentEnrollmentForeign;

    @Schema(description = "实际入组总人数（国内）")
    @TableField("total_actual_enrollment_domestic")
    private Integer totalActualEnrollmentDomestic;

    @Schema(description = "实际入组总人数（国外）")
    @TableField("total_actual_enrollment_foreign")
    private Integer totalActualEnrollmentForeign;

    @Schema(description = "第一例受试者签署知情同意书日期（国内）")
    @TableField("first_consent_date_domestic")
    private LocalDateTime firstConsentDateDomestic;

    @Schema(description = "第一例受试者签署知情同意书日期（国外）")
    @TableField("first_consent_date_foreign")
    private LocalDateTime firstConsentDateForeign;

    @Schema(description = "第一例受试者入组日期（国内）")
    @TableField("first_enrollment_date_domestic")
    private LocalDateTime firstEnrollmentDateDomestic;

    @Schema(description = "第一例受试者入组日期（国外）")
    @TableField("first_enrollment_date_foreign")
    private LocalDateTime firstEnrollmentDateForeign;

    @Schema(description = "完成日期（国内）")
    @TableField("completion_date_domestic")
    private LocalDateTime completionDateDomestic;

    @Schema(description = "完成日期（国外）")
    @TableField("completion_date_foreign")
    private LocalDateTime completionDateForeign;

    @Schema(description = "临床试验结果摘要")
    @TableField("clinical_trial_results_summary")
    private String clinicalTrialResultsSummary;

    @Schema(description = "是否分析")
    @TableField("is_analysis")
    private Boolean isAnalysis;
}
