package com.onework.boot.scrape.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-11-29
 */
@Getter
@Setter
@TableName("ow_cde_relationship")
public class CDERelationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uid")
    private Integer uid;

    /**
     * 机构名称
     */
    @TableField("institution_name")
    private String institutionName;

    /**
     * 主要研究者
     */
    @TableField("researcher_name")
    private String researcherName;

    /**
     * 申办方名称
     */
    @TableField("sponsor_name")
    private String sponsorName;

    /**
     * 登记号
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 试验方案编号
     */
    @TableField("trial_protocol_number")
    private String trialProtocolNumber;

    /**
     * 试验专业题目
     */
    @TableField("professional_title")
    private String professionalTitle;

    /**
     * 盲法
     */
    @TableField("blinding")
    private String blinding;

    /**
     * 适应症
     */
    @TableField("indication")
    private String indication;

    /**
     * 设计类型
     */
    @TableField("drug_type")
    private String drugType;

    /**
     * 药物名称
     */
    @TableField("drug_name")
    private String drugName;

    /**
     * 试验分期
     */
    @TableField("trial_phase")
    private String trialPhase;

    /**
     * 试验分类
     */
    @TableField("trial_classification")
    private String trialClassification;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 是否删除
     */
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
