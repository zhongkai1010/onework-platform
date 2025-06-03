package com.onework.boot.module.crawler.dal.dataobject;

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
 * @since 2025-05-30
 */
@Getter
@Setter
@TableName("ow_cde_researcher")
public class CDEResearcher extends BaseDO {

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
}
