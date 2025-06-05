package com.onework.boot.module.system.api.sms.dto.code;

import com.onework.boot.framework.common.validation.InEnum;
import com.onework.boot.framework.common.validation.Mobile;
import com.onework.boot.module.system.enums.sms.SmsSceneEnum;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.Accessors;
import org.checkerframework.checker.units.qual.A;

/**
 * 短信验证码的使用 Request DTO
 *
 */
@Data
@Accessors(chain = true)
public class SmsCodeUseReqDTO {

    /**
     * 手机号
     */
    @Mobile
    @NotEmpty(message = "手机号不能为空")
    private String mobile;
    /**
     * 发送场景
     */
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;
    /**
     * 验证码
     */
    @NotEmpty(message = "验证码")
    private String code;
    /**
     * 使用 IP
     */
    @NotEmpty(message = "使用 IP 不能为空")
    private String usedIp;

}
