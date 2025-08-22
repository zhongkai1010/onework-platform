package com.onework.boot.module.system.controller.admin.captcha;

import com.onework.boot.framework.captcha.core.service.CaptchaService;
import com.onework.boot.framework.captcha.core.db.CaptchaDto;
import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.module.system.controller.admin.captcha.vo.CaptchaRespVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.onework.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 验证码")
@RestController("adminCaptchaController")
@RequestMapping("/system/captcha")
public class CaptchaController {

    private final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @RequestMapping
    @Operation(summary = "获得验证码")
    @PermitAll
    public CommonResult<CaptchaRespVO> get() {
        CaptchaDto respVO = captchaService.generate();
        CaptchaRespVO vo = new CaptchaRespVO();
        vo.setSceneId(respVO.getSceneId());
        vo.setBase64(respVO.getImage());
        return success(vo);
    }
}
