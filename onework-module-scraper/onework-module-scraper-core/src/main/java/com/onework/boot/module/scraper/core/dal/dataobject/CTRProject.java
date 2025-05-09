package com.onework.boot.module.scraper.core.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 临床试验项目�?
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Getter
@Setter
@TableName("ow_ctr_project")
public class CTRProject extends BaseDO {

    /**
     * 主键
     */
    @TableId("uid")
    private String uid;

    /**
     * 注册�?
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 最近更新日�?
     */
    @TableField("last_refreshed_date")
    private LocalDateTime lastRefreshedDate;

    /**
     * 注册时间
     */
    @TableField("registration_date")
    private LocalDateTime registrationDate;

    /**
     * 注册号状�?
     */
    @TableField("registration_status")
    private String registrationStatus;

    /**
     * Registration Status
     */
    @TableField("registration_status_en")
    private String registrationStatusEn;

    /**
     * 注册题目
     */
    @TableField("public_title")
    private String publicTitle;

    /**
     * Public Title
     */
    @TableField("public_title_en")
    private String publicTitleEn;

    /**
     * 注册题目简�?
     */
    @TableField("english_acronym")
    private String englishAcronym;

    /**
     * English Acronym
     */
    @TableField("english_acronym_en")
    private String englishAcronymEn;

    /**
     * 研究课题的正式科学名�?
     */
    @TableField("scientific_title")
    private String scientificTitle;

    /**
     * Scientific Title
     */
    @TableField("scientific_title_en")
    private String scientificTitleEn;

    /**
     * 研究课题代号(代码)
     */
    @TableField("study_subject_id")
    private String studySubjectId;

    /**
     * 申请注册联系�?
     */
    @TableField("applicant")
    private String applicant;

    /**
     * Applicant
     */
    @TableField("applicant_en")
    private String applicantEn;

    /**
     * 研究负责�?
     */
    @TableField("study_leader")
    private String studyLeader;

    /**
     * Study Leader
     */
    @TableField("study_leader_en")
    private String studyLeaderEn;

    /**
     * 申请注册联系人电�?
     */
    @TableField("applicant_phone")
    private String applicantPhone;

    /**
     * 研究负责人电�?
     */
    @TableField("study_leader_phone")
    private String studyLeaderPhone;

    /**
     * 申请注册联系人传�?
     */
    @TableField("applicant_fax")
    private String applicantFax;

    /**
     * 研究负责人传�?
     */
    @TableField("study_leader_fax")
    private String studyLeaderFax;

    /**
     * 申请注册联系人电子邮�?
     */
    @TableField("applicant_email")
    private String applicantEmail;

    /**
     * 研究负责人电子邮�?
     */
    @TableField("study_leader_email")
    private String studyLeaderEmail;

    /**
     * 申请单位网址(自愿提供)
     */
    @TableField("applicant_website")
    private String applicantWebsite;

    /**
     * 研究负责人网址(自愿提供)
     */
    @TableField("study_leader_website")
    private String studyLeaderWebsite;

    /**
     * 申请注册联系人通讯地址
     */
    @TableField("applicant_address")
    private String applicantAddress;

    /**
     * Applicant Address
     */
    @TableField("applicant_address_en")
    private String applicantAddressEn;

    /**
     * 研究负责人通讯地址
     */
    @TableField("study_leader_address")
    private String studyLeaderAddress;

    /**
     * Study Leader Address
     */
    @TableField("study_leader_address_en")
    private String studyLeaderAddressEn;

    /**
     * 申请注册联系人邮政编�?
     */
    @TableField("applicant_postcode")
    private String applicantPostcode;

    /**
     * 研究负责人邮政编�?
     */
    @TableField("study_leader_postcode")
    private String studyLeaderPostcode;

    /**
     * 申请人所在单�?
     */
    @TableField("applicant_institution")
    private String applicantInstitution;

    /**
     * Applicant Institution
     */
    @TableField("applicant_institution_en")
    private String applicantInstitutionEn;

    /**
     * 研究负责人所在单�?
     */
    @TableField("leader_affiliation")
    private String leaderAffiliation;

    /**
     * Leader Affiliation
     */
    @TableField("leader_affiliation_en")
    private String leaderAffiliationEn;

    /**
     * 是否获伦理委员会批准
     */
    @TableField("ethic_committee_approved")
    private String ethicCommitteeApproved;

    /**
     * Ethic Committee Approved
     */
    @TableField("ethic_committee_approved_en")
    private String ethicCommitteeApprovedEn;

    /**
     * 伦理委员会批件文�?
     */
    @TableField("ethic_committee_no")
    private String ethicCommitteeNo;

    /**
     * 伦理委员会批件附�?
     */
    @TableField("ethic_committee_file")
    private String ethicCommitteeFile;

    /**
     * 批准本研究的伦理委员会名�?
     */
    @TableField("ethic_committee_name")
    private String ethicCommitteeName;

    /**
     * Ethic Committee Name
     */
    @TableField("ethic_committee_name_en")
    private String ethicCommitteeNameEn;

    /**
     * 伦理委员会批准日�?
     */
    @TableField("ethic_committee_approval_date")
    private LocalDateTime ethicCommitteeApprovalDate;

    /**
     * 伦理委员会联系人
     */
    @TableField("ethic_committee_contact_name")
    private String ethicCommitteeContactName;

    /**
     * Ethic Committee Contact Name
     */
    @TableField("ethic_committee_contact_name_en")
    private String ethicCommitteeContactNameEn;

    /**
     * 伦理委员会联系地址
     */
    @TableField("ethic_committee_contact_address")
    private String ethicCommitteeContactAddress;

    /**
     * Ethic Committee Contact Address
     */
    @TableField("ethic_committee_contact_address_en")
    private String ethicCommitteeContactAddressEn;

    /**
     * 伦理委员会联系人电话
     */
    @TableField("ethic_committee_contact_phone")
    private String ethicCommitteeContactPhone;

    /**
     * 伦理委员会联系人邮箱
     */
    @TableField("ethic_committee_contact_email")
    private String ethicCommitteeContactEmail;

    /**
     * 研究实施负责（组长）单位
     */
    @TableField("primary_sponsor")
    private String primarySponsor;

    /**
     * Primary Sponsor
     */
    @TableField("primary_sponsor_en")
    private String primarySponsorEn;

    /**
     * 研究实施负责（组长）单位地址
     */
    @TableField("primary_sponsor_address")
    private String primarySponsorAddress;

    /**
     * Primary Sponsor Address
     */
    @TableField("primary_sponsor_address_en")
    private String primarySponsorAddressEn;

    /**
     * 试验主办单位
     */
    @TableField("secondary_sponsor")
    private String secondarySponsor;

    /**
     * 经费或物资来�?
     */
    @TableField("funding_source")
    private String fundingSource;

    /**
     * Funding Source
     */
    @TableField("funding_source_en")
    private String fundingSourceEn;

    /**
     * 研究疾病
     */
    @TableField("target_disease")
    private String targetDisease;

    /**
     * Target Disease
     */
    @TableField("target_disease_en")
    private String targetDiseaseEn;

    /**
     * 研究疾病代码
     */
    @TableField("target_disease_code")
    private String targetDiseaseCode;

    /**
     * Target Disease Code
     */
    @TableField("target_disease_code_en")
    private String targetDiseaseCodeEn;

    /**
     * 研究类型
     */
    @TableField("study_type")
    private String studyType;

    /**
     * Study Type
     */
    @TableField("study_type_en")
    private String studyTypeEn;

    /**
     * 研究所处阶�?
     */
    @TableField("study_phase")
    private String studyPhase;

    /**
     * Study Phase
     */
    @TableField("study_phase_en")
    private String studyPhaseEn;

    /**
     * 研究设计
     */
    @TableField("study_design")
    private String studyDesign;

    /**
     * Study Design
     */
    @TableField("study_design_en")
    private String studyDesignEn;

    /**
     * 研究目的
     */
    @TableField("study_objectives")
    private String studyObjectives;

    /**
     * Study Objectives
     */
    @TableField("study_objectives_en")
    private String studyObjectivesEn;

    /**
     * 物成份或治疗方案详述
     */
    @TableField("treatment_description")
    private String treatmentDescription;

    /**
     * Treatment Description
     */
    @TableField("treatment_description_en")
    private String treatmentDescriptionEn;

    /**
     * 纳入标准
     */
    @TableField("inclusion_criteria")
    private String inclusionCriteria;

    /**
     * Inclusion Criteria
     */
    @TableField("inclusion_criteria_en")
    private String inclusionCriteriaEn;

    /**
     * 排除标准
     */
    @TableField("exclusion_criteria")
    private String exclusionCriteria;

    /**
     * Exclusion Criteria
     */
    @TableField("exclusion_criteria_en")
    private String exclusionCriteriaEn;

    /**
     * 研究实施开始时�?
     */
    @TableField("study_execution_start_time")
    private LocalDateTime studyExecutionStartTime;

    /**
     * 研究实施结束时间
     */
    @TableField("study_execution_end_time")
    private LocalDateTime studyExecutionEndTime;

    /**
     * 征募观察对象开始时�?
     */
    @TableField("recruiting_start_time")
    private LocalDateTime recruitingStartTime;

    /**
     * 征募观察对象结束时间
     */
    @TableField("recruiting_end_time")
    private LocalDateTime recruitingEndTime;

    /**
     * 干预措施
     */
    @TableField("interventions")
    private String interventions;

    /**
     * 研究实施地点
     */
    @TableField("recruitment_locations")
    private String recruitmentLocations;

    /**
     * 测量指标
     */
    @TableField("outcomes")
    private String outcomes;

    /**
     * 采集人体标本
     */
    @TableField("sample_collection")
    private String sampleCollection;

    /**
     * 征募研究对象情况
     */
    @TableField("recruiting_status")
    private String recruitingStatus;

    /**
     * Recruiting Status
     */
    @TableField("recruiting_status_en")
    private String recruitingStatusEn;

    /**
     * 年龄范围
     */
    @TableField("participant_age")
    private String participantAge;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 随机方法
     */
    @TableField("randomization_method")
    private String randomizationMethod;

    /**
     * Randomization Method
     */
    @TableField("randomization_method_en")
    private String randomizationMethodEn;

    /**
     * 是否公开试验完成后的统计结果
     */
    @TableField("result_publication")
    private String resultPublication;

    /**
     * 盲法
     */
    @TableField("blinding")
    private String blinding;

    /**
     * Blinding
     */
    @TableField("blinding_en")
    private String blindingEn;

    /**
     * 试验完成后的统计结果
     */
    @TableField("final_results")
    private String finalResults;

    /**
     * Final Results
     */
    @TableField("final_results_en")
    private String finalResultsEn;

    /**
     * 是否共享原始数据
     */
    @TableField("ipd_sharing")
    private String ipdSharing;

    /**
     * 共享原始数据的方�?
     */
    @TableField("ipd_sharing_method")
    private String ipdSharingMethod;

    /**
     * IPD Sharing Method
     */
    @TableField("ipd_sharing_method_en")
    private String ipdSharingMethodEn;

    /**
     * 数据采集和管�?
     */
    @TableField("data_management")
    private String dataManagement;

    /**
     * Data Management
     */
    @TableField("data_management_en")
    private String dataManagementEn;

    /**
     * 数据与安全监察委员会
     */
    @TableField("safety_committee")
    private String safetyCommittee;

    /**
     * Safety Committee
     */
    @TableField("safety_committee_en")
    private String safetyCommitteeEn;
}
