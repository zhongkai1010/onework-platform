package com.onework.boot.module.infra.api.logger;

import com.onework.boot.framework.common.api.logger.ApiErrorLogCommonApi;
import com.onework.boot.framework.common.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.onework.boot.module.infra.service.logger.ApiErrorLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志的 API 接口
 *
 */
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogCommonApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
    }

}
