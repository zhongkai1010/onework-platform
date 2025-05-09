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
@TableName("sys_operation_record")
public class SysOperationRecord extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 操作模块
     */
    @TableField("module")
    private String module;

    /**
     * 操作功能
     */
    @TableField("description")
    private String description;

    /**
     * 调用方法
     */
    @TableField("method")
    private String method;

    /**
     * 请求参数
     */
    @TableField("params")
    private String params;

    /**
     * 返回结果
     */
    @TableField("result")
    private String result;

    /**
     * 异常信息
     */
    @TableField("error")
    private String error;

    /**
     * 状�? 0成功, 1异常
     */
    @TableField("status")
    private Byte status;

    /**
     * 消耗时�? 单位毫秒
     */
    @TableField("spend_time")
    private Long spendTime;

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
     * 请求地址
     */
    @TableField("url")
    private String url;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

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
     * 备注
     */
    @TableField("comments")
    private String comments;
}
