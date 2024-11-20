package com.onework.boot.data.entity;

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
 * @since 2024-11-20
 */
@Getter
@Setter
@TableName("ow_ctr_collection_record")
public class CTRCollectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("uid")
    private String uid;

    /**
     * 注册号
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 注册题目
     */
    @TableField("registration_title")
    private String registrationTitle;

    /**
     * 研究类型
     */
    @TableField("study_type")
    private String studyType;

    /**
     * 注册时间
     */
    @TableField("registration_time")
    private LocalDateTime registrationTime;

    /**
     * 申请人所在单位
     */
    @TableField("applicant_institution")
    private String applicantInstitution;

    /**
     * 项目链接
     */
    @TableField("project_link")
    private String projectLink;

    /**
     * 项目采集时间
     */
    @TableField("project_collection_time")
    private LocalDateTime projectCollectionTime;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 表格采集时间
     */
    @TableField("table_collection_time")
    private LocalDateTime tableCollectionTime;

    /**
     * 历史版本链接
     */
    @TableField("history_version_link")
    private String historyVersionLink;

    /**
     * 是否解析
     */
    @TableField("is_parsed")
    private Boolean isParsed;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
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
