package com.onework.boot.framework.captcha.core.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;

/**
 * 验证码校验请求数据传输对象
 * <p>用于封装前端提交的验证码校验所需信息。</p>
 */
@Data
public class CaptchaVerificationDto {

    /**
     * 验证码场景ID
     * <p>用于唯一标识一次验证码交互。</p>
     */
    @NotEmpty(message = "场景ID不能为空")
    @Schema(description = "场景ID", example = "47312bdd-026d-41d4-ba25-e53d033ce127")
    String sceneId;

    /**
     * 用户输入的验证码内容
     * <p>可以是算术结果、字符、或其它类型的验证码。</p>
     */
    @NotEmpty(message = "验证码内容不能为空")
    @Schema(description = "验证码内容", example = "a1b2" )
    String captchaVerification;

    /**
     * 开启验证码校验的分组标记（用于分组校验）
     */
    public interface CodeEnableGroup {
    }
} 