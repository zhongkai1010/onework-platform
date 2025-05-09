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
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private String menuId;
}
