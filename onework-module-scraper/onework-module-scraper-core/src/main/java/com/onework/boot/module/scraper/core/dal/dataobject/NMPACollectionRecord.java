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
@TableName("ow_nmpa_collection_record")
public class NMPACollectionRecord extends BaseDO {

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
}
