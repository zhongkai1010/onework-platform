package com.onework.boot.framework.captcha.core.db;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 验证码信息传输对象
 * <p>用于封装生成的验证码相关信息。</p>
 */
@Schema(description = "验证码信息传输对象")
@Data
public class CaptchaDto {
    /**
     * 验证码场景ID
     */
    @Schema(description = "验证码场景ID", example = "47312bdd-026d-41d4-ba25-e53d033ce127")
    String sceneId;
    /**
     * 验证码图片（Base64编码）
     */
    @Schema(description = "验证码图片（Base64编码）", example = "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
    String image;
    /**
     * 验证码类型
     */
    @Schema(description = "验证码类型", example = "ARITHMETIC")
    String type;
}