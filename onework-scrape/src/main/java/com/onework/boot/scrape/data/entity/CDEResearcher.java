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
@TableName("ow_cde_researcher")
public class CDEResearcher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uid")
    private String uid;

    /**
     * 研究者名称
     */
    @TableField("researcher_name")
    private String researcherName;

    /**
     * 机构名称
     */
    @TableField("institution_name")
    private String institutionName;

    /**
     * 学位
     */
    @TableField("degree")
    private String degree;

    /**
     * 职称
     */
    @TableField("title")
    private String title;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

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
