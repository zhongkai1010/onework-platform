package com.onework.boot.scrape.dal.dataobject;

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
 * @since 2024-12-14
 */
@Getter
@Setter
@TableName("ow_nmpa_collection_record")
public class NmpaCollectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("uid")
    private String uid;

    /**
     * 企业名称
     */
    @TableField("companyName")
    private String companyName;

    /**
     * 许可证书编号
     */
    @TableField("licenseNumber")
    private String licenseNumber;

    /**
     * 社会信用代码
     */
    @TableField("socialCreditCode")
    private String socialCreditCode;

    /**
     * 详情链接
     */
    @TableField("url")
    private String url;

    /**
     * 创建时间
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
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
