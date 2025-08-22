package com.onework.boot.framework.captcha.core.service.impl;

import com.onework.boot.framework.captcha.config.CaptchaProperties;
import com.onework.boot.framework.captcha.core.service.CaptchaService;
import com.onework.boot.framework.captcha.core.db.CaptchaDto;
import com.onework.boot.framework.captcha.core.db.CaptchaVerificationDto;
import com.pig4cloud.captcha.ArithmeticCaptcha;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 */
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    private final CaptchaProperties captchaProperties;
    private final StringRedisTemplate redisTemplate;

    @Override
    public CaptchaDto generate() {
        // 生成验证码
        Captcha captcha = createCaptcha();
        String uuid = UUID.randomUUID().toString();
        String code = captcha.text().toLowerCase();

        // 保存验证码
        String key = captchaProperties.getPrefix() + uuid;
        redisTemplate.opsForValue().set(key, code, captchaProperties.getExpireMinutes(), TimeUnit.MINUTES);

        // 返回验证码信息
        CaptchaDto dto = new CaptchaDto();
        dto.setSceneId(uuid);
        dto.setImage(captcha.toBase64());
        dto.setType(captchaProperties.getType().name());
        return dto;
    }

    @Override
    public boolean validate(CaptchaVerificationDto dto) {
        if (!captchaProperties.isEnabled()) {
            return true;
        }
        if (dto == null || dto.getSceneId() == null || dto.getCaptchaVerification() == null) {
            return false;
        }
        String key = captchaProperties.getPrefix() + dto.getSceneId();
        String savedCode = redisTemplate.opsForValue().get(key);
        if (savedCode == null) {
            return false;
        }
        redisTemplate.delete(key);
        return savedCode.equals(dto.getCaptchaVerification().toLowerCase());
    }

    @Override
    public void delete(String sceneId) {
        String key = captchaProperties.getPrefix() + sceneId;
        redisTemplate.delete(key);
    }

    /**
     * 创建验证码
     */
    private Captcha createCaptcha() {
        Captcha captcha;
        switch (captchaProperties.getType()) {
            case ARITHMETIC:
                captcha = new ArithmeticCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
                break;
            case CHARACTER:
                captcha = new SpecCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLength());
                break;
            case GIF:
                captcha = new com.pig4cloud.captcha.GifCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight(), captchaProperties.getLength());
                break;
            default:
                captcha = new ArithmeticCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());
        }
        return captcha;
    }
} 