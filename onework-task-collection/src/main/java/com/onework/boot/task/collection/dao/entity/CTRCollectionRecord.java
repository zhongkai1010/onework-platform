package com.onework.boot.task.collection.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author onework
 * @since 2025-01-17
 */
@Getter
@Setter
@TableName("ow_ctr_collection_record")
public class CTRCollectionRecord extends BaseDO {

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
}
