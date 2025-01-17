package com.onework.boot.task.collection.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

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
@TableName("ow_cde_sponsor")
public class CDESponsor extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 申办方名称
     */
    @TableField("sponsor_name")
    private String sponsorName;

    /**
     * 联系人姓名
     */
    @TableField("contact_person_name")
    private String contactPersonName;

    /**
     * 联系人座机
     */
    @TableField("contact_landline")
    private String contactLandline;

    /**
     * 联系人手机号
     */
    @TableField("contact_mobile")
    private String contactMobile;

    /**
     * 联系人Email
     */
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 联系人邮政地址
     */
    @TableField("contact_address")
    private String contactAddress;

    /**
     * 联系人邮政编码
     */
    @TableField("contact_postcode")
    private String contactPostcode;

    /**
     * CDE项目登记号集合
     */
    @TableField("projects")
    private String projects;
}
