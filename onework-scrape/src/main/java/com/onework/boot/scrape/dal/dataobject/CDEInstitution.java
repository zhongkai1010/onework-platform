package com.onework.boot.scrape.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
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
@TableName("ow_cde_institution")
@Schema(name = "CDEInstitution", description = "")
public class CDEInstitution extends BaseDO {

    @TableId("uid")
    private String uid;

    @Schema(description = "机构名称")
    @TableField("institution_name")
    private String institutionName;

    @Schema(description = "国家")
    @TableField("country")
    private String country;

    @Schema(description = "省份")
    @TableField("province")
    private String province;

    @Schema(description = "城市")
    @TableField("city")
    private String city;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "邮编")
    @TableField("postal_code")
    private String postalCode;

    @Schema(description = "CDE项目登记号集合")
    @TableField("projects")
    private String projects;
}
