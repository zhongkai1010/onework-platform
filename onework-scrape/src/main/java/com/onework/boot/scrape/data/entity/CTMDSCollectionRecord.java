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
 * 临床机构信息表
 * </p>
 *
 * @author baomidou
 * @since 2024-12-03
 */
@Getter
@Setter
@TableName("ow_ctmds_collection_record")
public class CTMDSCollectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("uid")
    private String uid;

    /**
     * 外部ID
     */
    @TableField("external_id")
    private String externalId;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 备案号
     */
    @TableField("record_number")
    private String recordNumber;

    /**
     * 机构名称
     */
    @TableField("institution_name")
    private String institutionName;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contactPerson;

    /**
     * 联系方式
     */
    @TableField("contact_info")
    private String contactInfo;

    /**
     * 备案状态
     */
    @TableField("record_status")
    private String recordStatus;

    /**
     * 机构级别
     */
    @TableField("institution_level")
    private String institutionLevel;

    /**
     * 其他机构地址
     */
    @TableField("alternate_address")
    private String alternateAddress;

    /**
     * 首次备案时间
     */
    @TableField("first_record_date")
    private LocalDateTime firstRecordDate;

    /**
     * 备案时间
     */
    @TableField("record_date")
    private LocalDateTime recordDate;

    /**
     * 专业与研究者
     */
    @TableField("specialization_and_researchers")
    private String specializationAndResearchers;

    /**
     * 检查信息
     */
    @TableField("inspection_info")
    private String inspectionInfo;

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
