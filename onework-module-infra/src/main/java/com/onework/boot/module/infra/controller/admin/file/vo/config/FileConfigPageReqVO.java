package com.onework.boot.module.infra.controller.admin.file.vo.config;

import com.onework.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import static com.onework.boot.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "管理后台 - 文件配置分页 Request VO")
@Data
public class FileConfigPageReqVO extends PageParam {

    @Schema(description = "配置名", example = "S3 - 阿里云")
    private String name;

    @Schema(description = "存储器", example = "1")
    private Integer storage;

    @Schema(description = "创建时间", example = "[2022-07-01 00:00:00, 2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}