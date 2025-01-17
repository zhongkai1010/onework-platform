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
@TableName("ow_bohe_company")
@Schema(name = "BOHECompany", description = "")
public class BOHECompany extends BaseDO {

    @TableId("uid")
    private String uid;

    @Schema(description = "企业名称")
    @TableField("company_name")
    private String companyName;

    @Schema(description = "法定代表人")
    @TableField("legal_representative")
    private String legalRepresentative;

    @Schema(description = "注册资本")
    @TableField("registered_capital")
    private String registeredCapital;

    @Schema(description = "成立时间")
    @TableField("establishment_date")
    private LocalDateTime establishmentDate;

    @Schema(description = "企业地址")
    @TableField("address")
    private String address;

    @Schema(description = "经营范围")
    @TableField("business_scope")
    private String businessScope;

    @Schema(description = "公司简介")
    @TableField("company_profile")
    private String companyProfile;

    @Schema(description = "图标链接")
    @TableField("logo_url")
    private String logoUrl;

    @Schema(description = "企业别名")
    @TableField("alias")
    private String alias;

    @Schema(description = "网站地址")
    @TableField("website_url")
    private String websiteUrl;
}
