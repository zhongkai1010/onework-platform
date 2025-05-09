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
 * 
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Getter
@Setter
@TableName("ow_cde_collection_record")
public class CDECollectionRecord extends BaseDO {

    /**
     * 唯一值，不重�?
     */
    @TableId("uid")
    private String uid;

    /**
     * 登记�?
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 记录时间
     */
    @TableField("record_date")
    private LocalDateTime recordDate;

    /**
     * 是否下载页面
     */
    @TableField("is_download")
    private Boolean isDownload;

    /**
     * 文件地址
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件下载时间
     */
    @TableField("download_date")
    private LocalDateTime downloadDate;

    /**
     * 是否解析处理
     */
    @TableField("is_parse")
    private Boolean isParse;

    /**
     * 解析的时�?
     */
    @TableField("parse_date")
    private LocalDateTime parseDate;
}
