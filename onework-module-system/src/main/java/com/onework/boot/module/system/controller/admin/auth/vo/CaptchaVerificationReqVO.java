package com.onework.boot.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Schema(description = "验证码验证 Request VO")
@Data
public class CaptchaVerificationReqVO {

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "验证码不能为空")
    private String captchaVerification;

} 