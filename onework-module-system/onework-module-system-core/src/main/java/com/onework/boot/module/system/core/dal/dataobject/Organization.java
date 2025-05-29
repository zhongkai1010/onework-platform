package com.onework.boot.module.system.core.dal.dataobject;

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
 * @since 2025-05-26
 */
@Getter
@Setter
@TableName("sys_organization")
public class Organization extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 机构代码
     */
    @TableField("organization_code")
    private String organizationCode;

    /**
     * 机构名称
     */
    @TableField("organization_name")
    private String organizationName;

    /**
     * 机构全称
     */
    @TableField("organization_full_name")
    private String organizationFullName;

    /**
     * 机构类型, 字典标识
     */
    @TableField("organization_type")
    private String organizationType;

    /**
     * 机构类型名称
     */
    @TableField("organization_type_name")
    private String organizationTypeName;

    /**
     * 上级id, 0是顶级
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 负责人id
     */
    @TableField("leader_id")
    private String leaderId;

    /**
     * 负责人姓名
     */
    @TableField("leader_nickname")
    private String leaderNickname;

    /**
     * 负责人账号
     */
    @TableField("leader_username")
    private String leaderUsername;

    /**
     * 排序号
     */
    @TableField("sort_number")
    private Integer sortNumber;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;
}
