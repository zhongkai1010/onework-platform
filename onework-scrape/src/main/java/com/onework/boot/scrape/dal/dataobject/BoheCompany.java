package com.onework.boot.scrape.dal.dataobject;

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
 * @since 2024-12-14
 */
@Getter
@Setter
@TableName("ow_bohe_company")
public class BoheCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uid")
    private String uid;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 法定代表人
     */
    @TableField("legal_representative")
    private String legalRepresentative;

    /**
     * 注册资本
     */
    @TableField("registered_capital")
    private String registeredCapital;

    /**
     * 成立时间
     */
    @TableField("establishment_date")
    private LocalDateTime establishmentDate;

    /**
     * 企业地址
     */
    @TableField("address")
    private String address;

    /**
     * 经营范围
     */
    @TableField("business_scope")
    private String businessScope;

    /**
     * 公司简介
     */
    @TableField("company_profile")
    private String companyProfile;

    /**
     * 图标链接
     */
    @TableField("logo_url")
    private String logoUrl;

    /**
     * 企业别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 网站地址
     */
    @TableField("website_url")
    private String websiteUrl;

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
