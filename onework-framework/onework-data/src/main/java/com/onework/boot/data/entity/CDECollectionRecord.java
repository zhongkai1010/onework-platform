package com.onework.boot.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2024-11-20
 */
@Getter
@Setter
@TableName("ow_cde_collection_record")
public class CDECollectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一值，不重复
     */
    @TableId("uid")
    private String uid;

    /**
     * 登记号
     */
    @TableField("registration_number")
    private String registrationNumber;

    /**
     * 采集时间
     */
    @TableField("collection_date")
    private LocalDateTime collectionDate;

    /**
     * 是否解析处理
     */
    @TableField("parse_handle")
    private Boolean parseHandle;

    /**
     * 解析的时间
     */
    @TableField("parse_date")
    private LocalDateTime parseDate;

    /**
     * 文件地址
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 是否删除
     */
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
