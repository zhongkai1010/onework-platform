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
@TableName("sys_user_file")
public class SysUserFile extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 文件名称
     */
    @TableField("name")
    private String name;

    /**
     * 文件路径
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
    private Integer length;

    /**
     * 是否是文件夹, 0�? 1�?
     */
    @TableField("is_directory")
    private Byte isDirectory;

    /**
     * 上级id
     */
    @TableField("parent_id")
    private String parentId;

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
     * 备注
     */
    @TableField("comments")
    private String comments;
}
