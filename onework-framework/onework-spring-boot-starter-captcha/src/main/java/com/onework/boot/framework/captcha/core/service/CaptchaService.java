package com.onework.boot.framework.captcha.core.service;

import com.onework.boot.framework.captcha.core.db.CaptchaDto;
import com.onework.boot.framework.captcha.core.db.CaptchaVerificationDto;


/**
 * 验证码服务接口
 */
public interface CaptchaService {

    /**
     * 生成验证码
     *
     * @return 验证码信息
     */
    CaptchaDto generate();

    /**
     * 校验验证码
     *
     * @param reqVO 验证码校验请求
     * @return 是否校验通过
     */
    boolean validate(CaptchaVerificationDto reqVO);

    /**
     * 删除验证码
     *
     * @param sceneId 验证码场景ID
     */
    void delete(String sceneId);
}
