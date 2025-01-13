package com.onework.boot.scrape.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Getter
@Setter
@TableName("ow_nmpa_collection_record")
@Schema(name = "NMPACollectionRecord", description = "")
public class NMPACollectionRecord extends BaseDO {

    @TableId("uid")
    private String uid;

    @Schema(description = "企业名称")
    @TableField("companyName")
    private String companyName;

    @Schema(description = "许可证书编号")
    @TableField("licenseNumber")
    private String licenseNumber;

    @Schema(description = "社会信用代码")
    @TableField("socialCreditCode")
    private String socialCreditCode;

    @Schema(description = "详情链接")
    @TableField("url")
    private String url;

    @Schema(description = "创建时间")
    @TableField("created_at")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
