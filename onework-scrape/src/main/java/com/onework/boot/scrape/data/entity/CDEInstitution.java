package com.onework.boot.scrape.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-12-11
 */
@Getter
@Setter
@TableName("ow_cde_institution")
public class CDEInstitution implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * CDE项目登记号集合
     */
    @TableField("projects")
    private String projects;

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
