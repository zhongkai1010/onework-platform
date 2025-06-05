package com.onework.boot.module.infra.api.logger;

import com.onework.boot.framework.common.api.logger.ApiAccessLogCommonApi;
import com.onework.boot.framework.common.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.onework.boot.module.infra.service.logger.ApiAccessLogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * API 访问日志的 API 实现类
 *
 */
@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogCommonApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
    }

}
