package com.onework.boot.module.system.util;

import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onework.boot.framework.security.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * JWT工具类
 * 提供token的生成、验证、刷新和解析功能
 */
@Slf4j
public class JwtUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final int MIN_SECRET_LENGTH = 32; // 最小密钥长度（字节）

    /**
     * 生成JWT Token
     *
     * @param loginUser  登录用户信息
     * @param secret     密钥
     * @param expiration 过期时间（毫秒）
     * @return JWT token字符串
     * @throws RuntimeException 如果token生成失败
     */
    public static String generateToken(LoginUser loginUser, String secret, long expiration) {
        try {
            // 验证参数
            if (loginUser == null) {
                throw new RuntimeException("LoginUser cannot be null");
            }
            if (expiration <= 0) {
                throw new RuntimeException("Expiration time must be positive");
            }
            if (secret == null || secret.trim().isEmpty()) {
                throw new RuntimeException("Secret key cannot be null or empty");
            }
            if (secret.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_LENGTH) {
                throw new RuntimeException("Secret key must be at least " + MIN_SECRET_LENGTH + " bytes long");
            }

            // 序列化LoginUser
            String loginUserJson = objectMapper.writeValueAsString(loginUser);
            
            // 使用SHA-256生成AES密钥并加密
            byte[] sha256Key = SecureUtil.sha256(secret).getBytes(StandardCharsets.UTF_8);
            byte[] aesKey = new byte[32];
            System.arraycopy(sha256Key, 0, aesKey, 0, 32);
            
            String encryptedLoginUser = SecureUtil.aes(aesKey).encryptBase64(loginUserJson);
            
            // 生成JWT Token
            Instant now = Instant.now();
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .subject(encryptedLoginUser)
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(now.plus(expiration, ChronoUnit.MILLIS)))
                    .signWith(key)
                    .json(new JacksonSerializer<>(objectMapper))
                    .compact();
        } catch (Exception e) {
            log.error("Failed to generate token: {}", e.getMessage());
            throw new RuntimeException("Failed to generate token: " + e.getMessage(), e);
        }
    }

    /**
     * 解析JWT Token并返回LoginUser对象
     * 如果token无效或解析失败，返回null
     *
     * @param token  JWT token
     * @param secret 密钥
     * @return LoginUser对象，如果token无效或解析失败则返回null
     */
    public static LoginUser parseToken(String token, String secret) {
        try {
            // 验证参数
            if (token == null || token.trim().isEmpty()) {
                throw new RuntimeException("Token cannot be null or empty");
            }
            if (secret == null || secret.trim().isEmpty()) {
                throw new RuntimeException("Secret key cannot be null or empty");
            }
            if (secret.getBytes(StandardCharsets.UTF_8).length < MIN_SECRET_LENGTH) {
                throw new RuntimeException("Secret key must be at least " + MIN_SECRET_LENGTH + " bytes long");
            }

            // 解析JWT Token
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .json(new JacksonDeserializer<>(objectMapper))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            
            // 获取加密的LoginUser并解密
            String encryptedLoginUser = claims.getSubject();
            byte[] sha256Key = SecureUtil.sha256(secret).getBytes(StandardCharsets.UTF_8);
            byte[] aesKey = new byte[32];
            System.arraycopy(sha256Key, 0, aesKey, 0, 32);
            String loginUserJson = SecureUtil.aes(aesKey).decryptStr(encryptedLoginUser);
            
            return objectMapper.readValue(loginUserJson, LoginUser.class);
        } catch (Exception e) {
            log.warn("Token validation failed: {}", e.getMessage());
            return null;
        }
    }
}
