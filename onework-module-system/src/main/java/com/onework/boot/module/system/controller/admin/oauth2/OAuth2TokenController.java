package com.onework.boot.module.system.controller.admin.oauth2;

import com.onework.boot.framework.common.pojo.CommonResult;
import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.common.util.object.BeanUtils;
import com.onework.boot.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenPageReqVO;
import com.onework.boot.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenRespVO;
import com.onework.boot.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.onework.boot.module.system.enums.logger.LoginLogTypeEnum;
import com.onework.boot.module.system.service.auth.AdminAuthService;
import com.onework.boot.module.system.service.oauth2.OAuth2TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static com.onework.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - OAuth2.0 令牌")
@RestController
@RequestMapping("/system/oauth2-token")
public class OAuth2TokenController {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private AdminAuthService authService;

    @GetMapping("/page")
    @Operation(summary = "获得访问令牌分页", description = "只返回有效期内的")
    @PreAuthorize("@ss.hasPermission('system:oauth2-token:page')")
    public CommonResult<PageResult<OAuth2AccessTokenRespVO>> getAccessTokenPage(@Valid OAuth2AccessTokenPageReqVO reqVO) {
        PageResult<OAuth2AccessTokenDO> pageResult = oauth2TokenService.getAccessTokenPage(reqVO);
        return success(BeanUtils.toBean(pageResult, OAuth2AccessTokenRespVO.class));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    @PreAuthorize("@ss.hasPermission('system:oauth2-token:delete')")
    public CommonResult<Boolean> deleteAccessToken(@RequestParam("accessToken") String accessToken) {
        authService.logout(accessToken, LoginLogTypeEnum.LOGOUT_DELETE.getType());
        return success(true);
    }

}
