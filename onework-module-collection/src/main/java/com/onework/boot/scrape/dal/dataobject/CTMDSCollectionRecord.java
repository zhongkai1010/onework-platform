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
 * 临床机构信息表
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Getter
@Setter
@TableName("ow_ctmds_collection_record")
@Schema(name = "CTMDSCollectionRecord", description = "临床机构信息表")
public class CTMDSCollectionRecord extends BaseDO {

    @Schema(description = "主键ID")
    @TableId("uid")
    private String uid;

    @Schema(description = "外部ID")
    @TableField("external_id")
    private String externalId;

    @Schema(description = "省份")
    @TableField("province")
    private String province;

    @Schema(description = "备案号")
    @TableField("record_number")
    private String recordNumber;

    @Schema(description = "机构名称")
    @TableField("institution_name")
    private String institutionName;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "联系人")
    @TableField("contact_person")
    private String contactPerson;

    @Schema(description = "联系方式")
    @TableField("contact_info")
    private String contactInfo;

    @Schema(description = "备案状态")
    @TableField("record_status")
    private String recordStatus;

    @Schema(description = "机构级别")
    @TableField("institution_level")
    private String institutionLevel;

    @Schema(description = "其他机构地址")
    @TableField("alternate_address")
    private String alternateAddress;

    @Schema(description = "首次备案时间")
    @TableField("first_record_date")
    private LocalDateTime firstRecordDate;

    @Schema(description = "备案时间")
    @TableField("record_date")
    private LocalDateTime recordDate;

    @Schema(description = "专业与研究者")
    @TableField("specialization_and_researchers")
    private String specializationAndResearchers;

    @Schema(description = "检查信息")
    @TableField("inspection_info")
    private String inspectionInfo;
}
