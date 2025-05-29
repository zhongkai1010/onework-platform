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
@TableName("sys_role")
public class Role extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 角色标识
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;

    /**
     * 排序号
     */
    @TableField("sort_number")
    private Integer sortNumber;
}
