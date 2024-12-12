package com.onework.boot.scrape.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-12-11
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
     * 历史版本链接
     */
    @TableField("history_version_link")
    private String historyVersionLink;

    /**
     * 项目采集时间
     */
    @TableField("record_date")
    private LocalDateTime recordDate;

    /**
     * 是否下载
     */
    @TableField("is_download")
    private Boolean isDownload;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 下载时间
     */
    @TableField("download_date")
    private LocalDateTime downloadDate;

    /**
     * 是否解析
     */
    @TableField("is_parse")
    private Boolean isParse;

    /**
     * 解析时间
     */
    @TableField("parse_date")
    private LocalDateTime parseDate;

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
