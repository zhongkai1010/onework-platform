package com.onework.boot.module.infra.framework.security.oauth2;

import com.onework.boot.framework.common.api.oauth2.OAuth2TokenCommonApi;
import com.onework.boot.framework.common.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import com.onework.boot.framework.common.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.onework.boot.framework.common.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2.0 Token API 实现类 */
@Service
public class OAuth2TokenCommonApiImpl implements OAuth2TokenCommonApi {

    @Override
    public OAuth2AccessTokenRespDTO createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        // 创建访问令牌
        OAuth2AccessTokenRespDTO respDTO = new OAuth2AccessTokenRespDTO();
        respDTO.setAccessToken("test-access-token");
        respDTO.setRefreshToken("test-refresh-token");
        respDTO.setUserId(reqDTO.getUserId());
        respDTO.setUserType(reqDTO.getUserType());
        respDTO.setExpiresTime(LocalDateTime.now().plusHours(24));
        return respDTO;
    }

    @Override
    public OAuth2AccessTokenCheckRespDTO checkAccessToken(String accessToken) {
        // 校验访问令牌
        OAuth2AccessTokenCheckRespDTO respDTO = new OAuth2AccessTokenCheckRespDTO();
        respDTO.setUserId(1L);
        respDTO.setUserType(1);
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("username", "admin");
        respDTO.setUserInfo(userInfo);
        respDTO.setTenantId(1L);
        respDTO.setExpiresTime(LocalDateTime.now().plusHours(24));
        return respDTO;
    }

    @Override
    public OAuth2AccessTokenRespDTO removeAccessToken(String accessToken) {
        // 移除访问令牌
        OAuth2AccessTokenRespDTO respDTO = new OAuth2AccessTokenRespDTO();
        respDTO.setAccessToken(accessToken);
        return respDTO;
    }

    @Override
    public OAuth2AccessTokenRespDTO refreshAccessToken(String refreshToken, String clientId) {
        // 刷新访问令牌
        OAuth2AccessTokenRespDTO respDTO = new OAuth2AccessTokenRespDTO();
        respDTO.setAccessToken("new-test-access-token");
        respDTO.setRefreshToken("new-test-refresh-token");
        respDTO.setUserId(1L);
        respDTO.setUserType(1);
        respDTO.setExpiresTime(LocalDateTime.now().plusHours(24));
        return respDTO;
    }
} 