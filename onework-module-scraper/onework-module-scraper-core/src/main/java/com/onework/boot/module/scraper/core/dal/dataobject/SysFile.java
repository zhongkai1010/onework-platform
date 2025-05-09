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
@TableName("sys_file")
public class SysFile extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 文件名称
     */
    @TableField("name")
    private String name;

    /**
     * 文件存储路径
     */
    @TableField("path")
    private String path;

    /**
     * 文件类型
     */
    @TableField("content_type")
    private String contentType;

    /**
     * 文件大小
     */
    @TableField("length")
    private Long length;

    /**
     * 文件访问地址
     */
    @TableField("url")
    private String url;

    /**
     * 文件下载地址
     */
    @TableField("download_url")
    private String downloadUrl;

    /**
     * 文件缩略图访问地址
     */
    @TableField("thumbnail")
    private String thumbnail;

    /**
     * 创建�?
     */
    @TableField("create_user_id")
    private String createUserId;

    /**
     * 创建人账�?
     */
    @TableField("create_username")
    private String createUsername;

    /**
     * 创建人名�?
     */
    @TableField("create_nickname")
    private String createNickname;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;
}
