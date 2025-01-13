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
@TableName("ow_cde_researcher")
@Schema(name = "CDEResearcher", description = "")
public class CDEResearcher extends BaseDO {

    @TableId("uid")
    private String uid;

    @Schema(description = "研究者名称")
    @TableField("researcher_name")
    private String researcherName;

    @Schema(description = "机构名称")
    @TableField("institution_name")
    private String institutionName;

    @Schema(description = "学位")
    @TableField("degree")
    private String degree;

    @Schema(description = "职称")
    @TableField("title")
    private String title;

    @Schema(description = "电话")
    @TableField("phone")
    private String phone;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "CDE项目登记号集合")
    @TableField("projects")
    private String projects;
}
