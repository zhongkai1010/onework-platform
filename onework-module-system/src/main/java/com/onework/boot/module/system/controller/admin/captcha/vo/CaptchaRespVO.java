package com.onework.boot.module.system.controller.admin.captcha.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 验证码 Response VO")
@Data
public class CaptchaRespVO {

    @Schema(description = "验证码图片", example = "data:image/gif;base64,R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
    private String base64;

    @Schema(description = "验证码KEY（场景ID）", example = "47312bdd-026d-41d4-ba25-e53d033ce127")
    private String sceneId;

}
