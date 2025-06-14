package com.onework.boot.framework.common.api.token.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TokenDataDto {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 租户编号
     */
    private Long tenantId;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
