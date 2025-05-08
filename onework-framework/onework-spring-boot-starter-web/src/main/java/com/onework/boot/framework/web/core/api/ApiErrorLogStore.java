package com.onework.boot.framework.web.core.api;

import com.onework.boot.framework.web.core.dto.ApiErrorLogDTO;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;

public interface ApiErrorLogStore {

    /**
     * 创建 API 错误日志
     *
     * @param createDTO 创建信息
     */
    default void createApiErrorLog(@Valid ApiErrorLogDTO createDTO){

    }

    /**
     * 【异步】创建 API 异常日志
     *
     * @param createDTO 异常日志 DTO
     */
    @Async
    default void createApiErrorLogAsync(ApiErrorLogDTO createDTO) {

        createApiErrorLog(createDTO);
    }
}
