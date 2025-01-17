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
 * 临床试验项目表
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Getter
@Setter
@TableName("ow_ctr_project")
@Schema(name = "CTRProject", description = "临床试验项目表")
public class CTRProject extends BaseDO {

    @Schema(description = "主键")
    @TableId("uid")
    private String uid;

    @Schema(description = "注册号")
    @TableField("registration_number")
    private String registrationNumber;

    @Schema(description = "最近更新日期")
    @TableField("last_refreshed_date")
    private LocalDateTime lastRefreshedDate;

    @Schema(description = "注册时间")
    @TableField("registration_date")
    private LocalDateTime registrationDate;

    @Schema(description = "注册号状态")
    @TableField("registration_status")
    private String registrationStatus;

    @Schema(description = "Registration Status")
    @TableField("registration_status_en")
    private String registrationStatusEn;

    @Schema(description = "注册题目")
    @TableField("public_title")
    private String publicTitle;

    @Schema(description = "Public Title")
    @TableField("public_title_en")
    private String publicTitleEn;

    @Schema(description = "注册题目简写")
    @TableField("english_acronym")
    private String englishAcronym;

    @Schema(description = "English Acronym")
    @TableField("english_acronym_en")
    private String englishAcronymEn;

    @Schema(description = "研究课题的正式科学名称")
    @TableField("scientific_title")
    private String scientificTitle;

    @Schema(description = "Scientific Title")
    @TableField("scientific_title_en")
    private String scientificTitleEn;

    @Schema(description = "研究课题代号(代码)")
    @TableField("study_subject_id")
    private String studySubjectId;

    @Schema(description = "申请注册联系人")
    @TableField("applicant")
    private String applicant;

    @Schema(description = "Applicant")
    @TableField("applicant_en")
    private String applicantEn;

    @Schema(description = "研究负责人")
    @TableField("study_leader")
    private String studyLeader;

    @Schema(description = "Study Leader")
    @TableField("study_leader_en")
    private String studyLeaderEn;

    @Schema(description = "申请注册联系人电话")
    @TableField("applicant_phone")
    private String applicantPhone;

    @Schema(description = "研究负责人电话")
    @TableField("study_leader_phone")
    private String studyLeaderPhone;

    @Schema(description = "申请注册联系人传真")
    @TableField("applicant_fax")
    private String applicantFax;

    @Schema(description = "研究负责人传真")
    @TableField("study_leader_fax")
    private String studyLeaderFax;

    @Schema(description = "申请注册联系人电子邮件")
    @TableField("applicant_email")
    private String applicantEmail;

    @Schema(description = "研究负责人电子邮件")
    @TableField("study_leader_email")
    private String studyLeaderEmail;

    @Schema(description = "申请单位网址(自愿提供)")
    @TableField("applicant_website")
    private String applicantWebsite;

    @Schema(description = "研究负责人网址(自愿提供)")
    @TableField("study_leader_website")
    private String studyLeaderWebsite;

    @Schema(description = "申请注册联系人通讯地址")
    @TableField("applicant_address")
    private String applicantAddress;

    @Schema(description = "Applicant Address")
    @TableField("applicant_address_en")
    private String applicantAddressEn;

    @Schema(description = "研究负责人通讯地址")
    @TableField("study_leader_address")
    private String studyLeaderAddress;

    @Schema(description = "Study Leader Address")
    @TableField("study_leader_address_en")
    private String studyLeaderAddressEn;

    @Schema(description = "申请注册联系人邮政编码")
    @TableField("applicant_postcode")
    private String applicantPostcode;

    @Schema(description = "研究负责人邮政编码")
    @TableField("study_leader_postcode")
    private String studyLeaderPostcode;

    @Schema(description = "申请人所在单位")
    @TableField("applicant_institution")
    private String applicantInstitution;

    @Schema(description = "Applicant Institution")
    @TableField("applicant_institution_en")
    private String applicantInstitutionEn;

    @Schema(description = "研究负责人所在单位")
    @TableField("leader_affiliation")
    private String leaderAffiliation;

    @Schema(description = "Leader Affiliation")
    @TableField("leader_affiliation_en")
    private String leaderAffiliationEn;

    @Schema(description = "是否获伦理委员会批准")
    @TableField("ethic_committee_approved")
    private String ethicCommitteeApproved;

    @Schema(description = "Ethic Committee Approved")
    @TableField("ethic_committee_approved_en")
    private String ethicCommitteeApprovedEn;

    @Schema(description = "伦理委员会批件文号")
    @TableField("ethic_committee_no")
    private String ethicCommitteeNo;

    @Schema(description = "伦理委员会批件附件")
    @TableField("ethic_committee_file")
    private String ethicCommitteeFile;

    @Schema(description = "批准本研究的伦理委员会名称")
    @TableField("ethic_committee_name")
    private String ethicCommitteeName;

    @Schema(description = "Ethic Committee Name")
    @TableField("ethic_committee_name_en")
    private String ethicCommitteeNameEn;

    @Schema(description = "伦理委员会批准日期")
    @TableField("ethic_committee_approval_date")
    private LocalDateTime ethicCommitteeApprovalDate;

    @Schema(description = "伦理委员会联系人")
    @TableField("ethic_committee_contact_name")
    private String ethicCommitteeContactName;

    @Schema(description = "Ethic Committee Contact Name")
    @TableField("ethic_committee_contact_name_en")
    private String ethicCommitteeContactNameEn;

    @Schema(description = "伦理委员会联系地址")
    @TableField("ethic_committee_contact_address")
    private String ethicCommitteeContactAddress;

    @Schema(description = "Ethic Committee Contact Address")
    @TableField("ethic_committee_contact_address_en")
    private String ethicCommitteeContactAddressEn;

    @Schema(description = "伦理委员会联系人电话")
    @TableField("ethic_committee_contact_phone")
    private String ethicCommitteeContactPhone;

    @Schema(description = "伦理委员会联系人邮箱")
    @TableField("ethic_committee_contact_email")
    private String ethicCommitteeContactEmail;

    @Schema(description = "研究实施负责（组长）单位")
    @TableField("primary_sponsor")
    private String primarySponsor;

    @Schema(description = "Primary Sponsor")
    @TableField("primary_sponsor_en")
    private String primarySponsorEn;

    @Schema(description = "研究实施负责（组长）单位地址")
    @TableField("primary_sponsor_address")
    private String primarySponsorAddress;

    @Schema(description = "Primary Sponsor Address")
    @TableField("primary_sponsor_address_en")
    private String primarySponsorAddressEn;

    @Schema(description = "试验主办单位")
    @TableField("secondary_sponsor")
    private String secondarySponsor;

    @Schema(description = "经费或物资来源")
    @TableField("funding_source")
    private String fundingSource;

    @Schema(description = "Funding Source")
    @TableField("funding_source_en")
    private String fundingSourceEn;

    @Schema(description = "研究疾病")
    @TableField("target_disease")
    private String targetDisease;

    @Schema(description = "Target Disease")
    @TableField("target_disease_en")
    private String targetDiseaseEn;

    @Schema(description = "研究疾病代码")
    @TableField("target_disease_code")
    private String targetDiseaseCode;

    @Schema(description = "Target Disease Code")
    @TableField("target_disease_code_en")
    private String targetDiseaseCodeEn;

    @Schema(description = "研究类型")
    @TableField("study_type")
    private String studyType;

    @Schema(description = "Study Type")
    @TableField("study_type_en")
    private String studyTypeEn;

    @Schema(description = "研究所处阶段")
    @TableField("study_phase")
    private String studyPhase;

    @Schema(description = "Study Phase")
    @TableField("study_phase_en")
    private String studyPhaseEn;

    @Schema(description = "研究设计")
    @TableField("study_design")
    private String studyDesign;

    @Schema(description = "Study Design")
    @TableField("study_design_en")
    private String studyDesignEn;

    @Schema(description = "研究目的")
    @TableField("study_objectives")
    private String studyObjectives;

    @Schema(description = "Study Objectives")
    @TableField("study_objectives_en")
    private String studyObjectivesEn;

    @Schema(description = "物成份或治疗方案详述")
    @TableField("treatment_description")
    private String treatmentDescription;

    @Schema(description = "Treatment Description")
    @TableField("treatment_description_en")
    private String treatmentDescriptionEn;

    @Schema(description = "纳入标准")
    @TableField("inclusion_criteria")
    private String inclusionCriteria;

    @Schema(description = "Inclusion Criteria")
    @TableField("inclusion_criteria_en")
    private String inclusionCriteriaEn;

    @Schema(description = "排除标准")
    @TableField("exclusion_criteria")
    private String exclusionCriteria;

    @Schema(description = "Exclusion Criteria")
    @TableField("exclusion_criteria_en")
    private String exclusionCriteriaEn;

    @Schema(description = "研究实施开始时间")
    @TableField("study_execution_start_time")
    private LocalDateTime studyExecutionStartTime;

    @Schema(description = "研究实施结束时间")
    @TableField("study_execution_end_time")
    private LocalDateTime studyExecutionEndTime;

    @Schema(description = "征募观察对象开始时间")
    @TableField("recruiting_start_time")
    private LocalDateTime recruitingStartTime;

    @Schema(description = "征募观察对象结束时间")
    @TableField("recruiting_end_time")
    private LocalDateTime recruitingEndTime;

    @Schema(description = "干预措施")
    @TableField("interventions")
    private String interventions;

    @Schema(description = "研究实施地点")
    @TableField("recruitment_locations")
    private String recruitmentLocations;

    @Schema(description = "测量指标")
    @TableField("outcomes")
    private String outcomes;

    @Schema(description = "采集人体标本")
    @TableField("sample_collection")
    private String sampleCollection;

    @Schema(description = "征募研究对象情况")
    @TableField("recruiting_status")
    private String recruitingStatus;

    @Schema(description = "Recruiting Status")
    @TableField("recruiting_status_en")
    private String recruitingStatusEn;

    @Schema(description = "年龄范围")
    @TableField("participant_age")
    private String participantAge;

    @Schema(description = "性别")
    @TableField("gender")
    private String gender;

    @Schema(description = "随机方法")
    @TableField("randomization_method")
    private String randomizationMethod;

    @Schema(description = "Randomization Method")
    @TableField("randomization_method_en")
    private String randomizationMethodEn;

    @Schema(description = "是否公开试验完成后的统计结果")
    @TableField("result_publication")
    private String resultPublication;

    @Schema(description = "盲法")
    @TableField("blinding")
    private String blinding;

    @Schema(description = "Blinding")
    @TableField("blinding_en")
    private String blindingEn;

    @Schema(description = "试验完成后的统计结果")
    @TableField("final_results")
    private String finalResults;

    @Schema(description = "Final Results")
    @TableField("final_results_en")
    private String finalResultsEn;

    @Schema(description = "是否共享原始数据")
    @TableField("ipd_sharing")
    private String ipdSharing;

    @Schema(description = "共享原始数据的方式")
    @TableField("ipd_sharing_method")
    private String ipdSharingMethod;

    @Schema(description = "IPD Sharing Method")
    @TableField("ipd_sharing_method_en")
    private String ipdSharingMethodEn;

    @Schema(description = "数据采集和管理")
    @TableField("data_management")
    private String dataManagement;

    @Schema(description = "Data Management")
    @TableField("data_management_en")
    private String dataManagementEn;

    @Schema(description = "数据与安全监察委员会")
    @TableField("safety_committee")
    private String safetyCommittee;

    @Schema(description = "Safety Committee")
    @TableField("safety_committee_en")
    private String safetyCommitteeEn;
}
