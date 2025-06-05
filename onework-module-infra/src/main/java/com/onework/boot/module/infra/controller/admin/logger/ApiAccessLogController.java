package com.onework.boot.module.infra.controller.admin.logger;

import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.common.util.object.BeanUtils;
import com.onework.boot.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import com.onework.boot.module.infra.controller.admin.logger.vo.apiaccesslog.ApiAccessLogRespVO;
import com.onework.boot.module.infra.dal.dataobject.logger.ApiAccessLogDO;
import com.onework.boot.module.infra.service.logger.ApiAccessLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.onework.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - API 访问日志")
@RestController
@RequestMapping("/infra/api-access-log")
@Validated
public class ApiAccessLogController {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @GetMapping("/page")
    @Operation(summary = "获得API 访问日志分页")
    @PreAuthorize("@ss.hasPermission('infra:api-access-log:query')")
    public CommonResult<PageResult<ApiAccessLogRespVO>> getApiAccessLogPage(@Valid ApiAccessLogPageReqVO pageReqVO) {
        PageResult<ApiAccessLogDO> pageResult = apiAccessLogService.getApiAccessLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ApiAccessLogRespVO.class));
    }
}
