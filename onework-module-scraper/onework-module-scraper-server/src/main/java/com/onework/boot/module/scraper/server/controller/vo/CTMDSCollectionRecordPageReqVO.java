package com.onework.boot.module.scraper.server.controller.vo;

import com.onework.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "临床机构信息表 分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CTMDSCollectionRecordPageReqVO extends PageParam
{

}