package com.onework.boot.scrape.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

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
@TableName("ow_cde_collection_record")
@Schema(name = "CDECollectionRecord", description = "")
public class CDECollectionRecord extends BaseDO {

    @Schema(description = "唯一值，不重复")
    @TableId("uid")
    private String uid;

    @Schema(description = "登记号")
    @TableField("registration_number")
    private String registrationNumber;

    @Schema(description = "记录时间")
    @TableField("record_date")
    private LocalDateTime recordDate;

    @Schema(description = "是否下载页面")
    @TableField("is_download")
    private Boolean isDownload;

    @Schema(description = "文件地址")
    @TableField("file_path")
    private String filePath;

    @Schema(description = "文件下载时间")
    @TableField("download_date")
    private LocalDateTime downloadDate;

    @Schema(description = "是否解析处理")
    @TableField("is_parse")
    private Boolean isParse;

    @Schema(description = "解析的时间")
    @TableField("parse_date")
    private LocalDateTime parseDate;
}
