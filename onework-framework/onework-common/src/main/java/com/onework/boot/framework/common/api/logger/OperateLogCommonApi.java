package com.onework.boot.framework.common.api.logger;

import com.onework.boot.framework.common.api.logger.dto.OperateLogCreateReqDTO;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 API 接口
 *
 */
public interface OperateLogCommonApi {

    /**
     * 创建操作日志
     *
     * @param createReqDTO 请求
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO createReqDTO);

    /**
     * 【异步】创建操作日志
     *
     * @param createReqDTO 请求
     */
    @Async
    default void createOperateLogAsync(OperateLogCreateReqDTO createReqDTO) {
        createOperateLog(createReqDTO);
    }

}