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
@TableName("ow_ctr_collection_record")
@Schema(name = "CTRCollectionRecord", description = "")
public class CTRCollectionRecord extends BaseDO {

    @Schema(description = "主键ID")
    @TableId("uid")
    private String uid;

    @Schema(description = "注册号")
    @TableField("registration_number")
    private String registrationNumber;

    @Schema(description = "注册题目")
    @TableField("registration_title")
    private String registrationTitle;

    @Schema(description = "研究类型")
    @TableField("study_type")
    private String studyType;

    @Schema(description = "注册时间")
    @TableField("registration_time")
    private LocalDateTime registrationTime;

    @Schema(description = "申请人所在单位")
    @TableField("applicant_institution")
    private String applicantInstitution;

    @Schema(description = "项目链接")
    @TableField("project_link")
    private String projectLink;

    @Schema(description = "历史版本链接")
    @TableField("history_version_link")
    private String historyVersionLink;

    @Schema(description = "项目采集时间")
    @TableField("record_date")
    private LocalDateTime recordDate;

    @Schema(description = "是否下载")
    @TableField("is_download")
    private Boolean isDownload;

    @Schema(description = "文件路径")
    @TableField("file_path")
    private String filePath;

    @Schema(description = "下载时间")
    @TableField("download_date")
    private LocalDateTime downloadDate;

    @Schema(description = "是否解析")
    @TableField("is_parse")
    private Boolean isParse;

    @Schema(description = "解析时间")
    @TableField("parse_date")
    private LocalDateTime parseDate;
}
