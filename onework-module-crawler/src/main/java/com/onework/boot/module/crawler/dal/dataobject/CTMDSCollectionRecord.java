package com.onework.boot.module.crawler.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 临床机构信息表
 * </p>
 *
 * @author onework
 * @since 2025-05-30
 */
@Getter
@Setter
@TableName("ow_ctmds_collection_record")
public class CTMDSCollectionRecord extends BaseDO {

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
}
