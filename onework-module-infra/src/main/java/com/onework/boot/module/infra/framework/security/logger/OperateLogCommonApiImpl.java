package com.onework.boot.module.infra.framework.security.logger;

import com.onework.boot.framework.common.api.logger.OperateLogCommonApi;
import com.onework.boot.framework.common.api.logger.dto.OperateLogCreateReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;

/**
 * 操作日志 API 实现类 */
@Slf4j
@Service
@Primary
public class OperateLogCommonApiImpl implements OperateLogCommonApi {

    @Override
    public void createOperateLog(OperateLogCreateReqDTO createReqDTO) {
        // 打印操作日志
        log.info("[操作日志] 用户({}) 操作({}) 模块({}) 业务编号({}) 操作内容({})",
                createReqDTO.getUserId(),
                createReqDTO.getSubType(),
                createReqDTO.getType(),
                createReqDTO.getBizId(),
                createReqDTO.getAction());
    }
} 