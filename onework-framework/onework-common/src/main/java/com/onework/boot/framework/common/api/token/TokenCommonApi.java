package com.onework.boot.framework.common.api.token;

import com.onework.boot.framework.common.api.token.dto.TokenDataDto;

/**
 * Token通用接口
 * 提供token的创建、验证、移除和刷新等功能
 */
public interface TokenCommonApi {

    /**
     * 创建token
     *
     * @param data token数据，包含用户信息等
     * @return token字符串
     */
    String createToken(TokenDataDto data);

    /**
     * 验证token
     *
     * @param token token字符串
     * @return token数据，包含用户信息和token状态
     */
    TokenDataDto checkToken(String token);

    /**
     * 移除token
     * 将token加入黑名单或从存储中删除
     *
     * @param token token字符串
     */
    TokenDataDto removeToken(String token);

    /**
     * 刷新token
     * 更新token的过期时间
     *
     * @param token token字符串
     * @param expiresIn 新的过期时间（毫秒）
     */
    void refreshToken(String token, long expiresIn);
}
