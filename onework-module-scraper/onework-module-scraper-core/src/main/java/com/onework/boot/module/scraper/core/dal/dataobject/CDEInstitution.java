package com.onework.boot.module.scraper.core.dal.dataobject;

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
 * @since 2025-05-09
 */
@Getter
@Setter
@TableName("ow_cde_institution")
public class CDEInstitution extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 机构名称
     */
    @TableField("institution_name")
    private String institutionName;

    /**
     * 国家
     */
    @TableField("country")
    private String country;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 邮编
     */
    @TableField("postal_code")
    private String postalCode;

    /**
     * CDE项目登记号集�?
     */
    @TableField("projects")
    private String projects;
}
