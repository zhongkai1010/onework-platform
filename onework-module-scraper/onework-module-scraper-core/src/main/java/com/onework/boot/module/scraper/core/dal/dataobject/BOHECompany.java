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
@TableName("ow_bohe_company")
public class BOHECompany extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 法定代表�?
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
     * 公司简�?
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
}
