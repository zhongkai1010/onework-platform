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
@TableName("sys_menu")
public class Menu extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 菜单id
     */
    @TableField("menu_id")
    private String menuId;

    /**
     * 上级id, 0是顶级
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField("title")
    private String title;

    /**
     * 菜单路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 菜单组件地址
     */
    @TableField("component")
    private String component;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 权限标识
     */
    @TableField("authority")
    private String authority;

    /**
     * 菜单类型, 0菜单, 1按钮
     */
    @TableField("menu_type")
    private Byte menuType;

    /**
     * 是否隐藏, 0否, 1是
     */
    @TableField("hide")
    private Byte hide;

    /**
     * 排序号
     */
    @TableField("sort_number")
    private Integer sortNumber;

    /**
     * 路由元信息
     */
    @TableField("meta")
    private String meta;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;
}
