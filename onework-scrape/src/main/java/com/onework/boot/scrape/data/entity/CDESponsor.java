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
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-11-29
 */
@Getter
@Setter
@TableName("ow_cde_sponsor")
public class CDESponsor implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
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
