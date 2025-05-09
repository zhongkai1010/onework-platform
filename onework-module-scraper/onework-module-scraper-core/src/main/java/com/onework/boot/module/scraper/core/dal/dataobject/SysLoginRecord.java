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
@TableName("sys_login_record")
public class SysLoginRecord extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户账号
     */
    @TableField("username")
    private String username;

    /**
     * 用户昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 操作类型, 0登录成功, 1登录失败, 2退出登�? 3续签token
     */
    @TableField("login_type")
    private Byte loginType;

    /**
     * ip地址
     */
    @TableField("ip")
    private String ip;

    /**
     * 浏览器类�?
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 设备名称
     */
    @TableField("device")
    private String device;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;
}
