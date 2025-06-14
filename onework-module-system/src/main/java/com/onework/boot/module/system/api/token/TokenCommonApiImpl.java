package com.onework.boot.module.system.api.token;

import com.onework.boot.framework.common.api.token.TokenCommonApi;
import com.onework.boot.framework.common.api.token.dto.TokenDataDto;
import com.onework.boot.framework.common.exception.ServiceException;
import com.onework.boot.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.onework.boot.framework.common.util.json.JsonUtils;
import com.onework.boot.framework.security.LoginUser;
import com.onework.boot.framework.security.config.SecurityProperties;
import com.onework.boot.module.system.util.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import static com.onework.boot.module.system.dal.redis.RedisKeyConstants.JWT_TOKEN;

@Service
@Validated
public class TokenCommonApiImpl implements TokenCommonApi {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public String createToken(TokenDataDto data) {
        // 转换为 LoginUser
        LoginUser loginUser = new LoginUser();
        loginUser.setId(data.getUserId());
        loginUser.setUserType(data.getUserType());
        loginUser.setTenantId(data.getTenantId());
        loginUser.setExpiresTime(data.getExpiresTime());

        // 生成 JWT token
        long expirationTime = data.getExpiresTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();
        String token = JwtUtils.generateToken(loginUser, securityProperties.getJwtSecretKey(), expirationTime);

        // 存储到 Redis
        String redisKey = formatKey(String.valueOf(data.getUserId()));
        String tokenData = JsonUtils.toJsonString(data);
        stringRedisTemplate.opsForValue().set(redisKey, tokenData, expirationTime, TimeUnit.MILLISECONDS);
        
        return token;
    }

    @Override
    public TokenDataDto checkToken(String token) {
        // 从 JWT token 中解析用户ID
        LoginUser loginUser = JwtUtils.parseToken(token, securityProperties.getJwtSecretKey());
        if (loginUser == null) {
            return null;
        }
        // 从 Redis 中获取 token 数据
        String redisKey = formatKey(String.valueOf(loginUser.getId()));
        String tokenData = stringRedisTemplate.opsForValue().get(redisKey);
        if (tokenData == null) {
            return null;
        }
        // 解析 token 数据
        return JsonUtils.parseObject(tokenData, TokenDataDto.class);
    }

    @Override
    public TokenDataDto removeToken(String token) {
        // 从 JWT token 中解析用户ID
        LoginUser loginUser = JwtUtils.parseToken(token, securityProperties.getJwtSecretKey());
        if (loginUser == null) {
            return null;
        }
        // 从 Redis 中获取 token 数据
        String redisKey = formatKey(String.valueOf(loginUser.getId()));
        String tokenData = stringRedisTemplate.opsForValue().get(redisKey);
        if (tokenData == null) {
            return null;
        }
        // 删除 token
        stringRedisTemplate.delete(redisKey);
        // 返回 token 数据
        return JsonUtils.parseObject(tokenData, TokenDataDto.class);
    }

    @Override
    public void refreshToken(String token, long expiresIn) {
        // 从 JWT token 中解析用户ID
        LoginUser loginUser = JwtUtils.parseToken(token, securityProperties.getJwtSecretKey());
        if (loginUser == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        // 从 Redis 中获取 token 数据
        String redisKey = formatKey(String.valueOf(loginUser.getId()));
        String tokenData = stringRedisTemplate.opsForValue().get(redisKey);
        if (tokenData == null) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        // 解析 token 数据
        TokenDataDto data = JsonUtils.parseObject(tokenData, TokenDataDto.class);
        // 更新过期时间
        data.setExpiresTime(LocalDateTime.now().plusSeconds(expiresIn / 1000));
        // 更新 Redis 中的数据
        stringRedisTemplate.opsForValue().set(redisKey, JsonUtils.toJsonString(data),
                expiresIn, TimeUnit.MILLISECONDS);
    }

    private static String formatKey(String userId) {
        return String.format(JWT_TOKEN, userId);
    }
}
