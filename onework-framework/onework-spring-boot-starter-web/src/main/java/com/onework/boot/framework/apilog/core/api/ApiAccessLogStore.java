package com.onework.boot.framework.apilog.core.api;

import com.onework.boot.framework.apilog.core.dto.ApiAccessLogDTO;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;


public interface ApiAccessLogStore {

    /**
     * 创建 API 访问日志
     *
     * @param dto 创建信息
     */
    default void createApiAccessLog(@Valid ApiAccessLogDTO dto) {
        System.out.println(dto.toString());
    }

    /**
     * 【异步】创建 API 访问日志
     *
     * @param dto 访问日志 DTO
     */
    @Async
    default void createApiAccessLogAsync(ApiAccessLogDTO dto) {
        createApiAccessLog(dto);
    }
}
