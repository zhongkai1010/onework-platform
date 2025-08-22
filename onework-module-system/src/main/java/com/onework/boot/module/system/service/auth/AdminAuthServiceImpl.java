package com.onework.boot.module.system.service.auth;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.annotations.VisibleForTesting;
import com.onework.boot.framework.captcha.core.db.CaptchaVerificationDto;
import com.onework.boot.framework.captcha.core.service.CaptchaService;
import com.onework.boot.framework.common.api.token.TokenCommonApi;
import com.onework.boot.framework.common.api.token.dto.TokenDataDto;
import com.onework.boot.framework.common.enums.CommonStatusEnum;
import com.onework.boot.framework.common.enums.UserTypeEnum;
import com.onework.boot.framework.common.util.monitor.TracerUtils;
import com.onework.boot.framework.common.util.servlet.ServletUtils;
import com.onework.boot.framework.common.util.validation.ValidationUtils;
import com.onework.boot.framework.security.config.SecurityProperties;
import com.onework.boot.framework.tenant.core.context.TenantContextHolder;
import com.onework.boot.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.onework.boot.module.system.api.sms.SmsCodeApi;
import com.onework.boot.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.onework.boot.module.system.controller.admin.auth.vo.*;
import com.onework.boot.module.system.convert.auth.AuthConvert;
import com.onework.boot.module.system.dal.dataobject.user.AdminUserDO;
import com.onework.boot.module.system.enums.logger.LoginLogTypeEnum;
import com.onework.boot.module.system.enums.logger.LoginResultEnum;
import com.onework.boot.module.system.enums.sms.SmsSceneEnum;
import com.onework.boot.module.system.service.logger.LoginLogService;
import com.onework.boot.module.system.service.member.MemberService;
import com.onework.boot.module.system.service.user.AdminUserService;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.onework.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.onework.boot.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.onework.boot.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author Onework源码
 */
@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {

    @Resource
    private AdminUserService userService;
    @Resource
    private LoginLogService loginLogService;
    @Resource
    private MemberService memberService;
    @Resource
    private Validator validator;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private TokenCommonApi tokenCommonApi;
    @Resource
    private SecurityProperties securityProperties;


    @Override
    public AdminUserDO authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        AdminUserDO user = userService.getUserByUsername(username);
        if (user == null) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (CommonStatusEnum.isDisable(user.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // 校验验证码
        validateCaptcha(reqVO);

        // 使用账号密码，进行登录
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @Override
    public void sendSmsCode(AuthSmsSendReqVO reqVO) {
        // 如果是重置密码场景，需要校验图形验证码是否正确
        if (Objects.equals(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene(), reqVO.getScene())) {
            if (!captchaService.validate(reqVO)) {
                throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
            }
        }

        // 登录场景，验证是否存在
        if (userService.getUserByMobile(reqVO.getMobile()) == null) {
            throw exception(AUTH_MOBILE_NOT_EXISTS);
        }
        // 发送验证码
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public AuthLoginRespVO smsLogin(AuthSmsLoginReqVO reqVO) {
        // 校验验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.ADMIN_MEMBER_LOGIN.getScene(), getClientIP()));

        // 获得用户信息
        AdminUserDO user = userService.getUserByMobile(reqVO.getMobile());
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user.getId(), reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE);
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    @VisibleForTesting
    void validateCaptcha(AuthLoginReqVO reqVO) {
        boolean response = doValidateCaptcha(reqVO);
        // 校验验证码
        if (!response) {
            // 创建登录失败日志（验证码不正确)
            createLoginLog(null, reqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
    }

    private boolean doValidateCaptcha(CaptchaVerificationDto reqVO) {
        ValidationUtils.validate(validator, reqVO, CaptchaVerificationDto.CodeEnableGroup.class);
        return captchaService.validate(reqVO);
    }

    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, LoginLogTypeEnum logType) {
        // 插入登陆日志
        createLoginLog(userId, username, logType, LoginResultEnum.SUCCESS);
        // 创建访问令牌
        TokenDataDto tokenData = new TokenDataDto();
        tokenData.setUserId(userId);
        tokenData.setUserType(UserTypeEnum.ADMIN.getValue());
        tokenData.setTenantId(TenantContextHolder.getTenantId());
        // 设置过期时间：当前时间 + 过期秒数
        tokenData.setExpiresTime(LocalDateTime.now().plus(securityProperties.getJwtExpiresIn(),ChronoUnit.MILLIS));
        String token = tokenCommonApi.createToken(tokenData);
        // 构建返回结果
        AuthLoginRespVO vo = new AuthLoginRespVO();
        vo.setToken(token);
        vo.setExpiresTime(LocalDateTime.now().plus(securityProperties.getJwtExpiresIn(),ChronoUnit.MILLIS));
        return vo;
    }

    @Override
    public void logout(String token, Integer logType) {
        // 删除访问令牌
        TokenDataDto tokenDataDto = tokenCommonApi.removeToken(token);
        if (tokenDataDto == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(tokenDataDto.getUserId(), tokenDataDto.getUserType(), logType);
    }

    private void createLogoutLog(Long userId, Integer userType, Integer logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        if (ObjectUtil.equal(getUserType().getValue(), userType)) {
            reqDTO.setUsername(getUsername(userId));
        } else {
            reqDTO.setUsername(memberService.getMemberUserMobile(userId));
        }
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        AdminUserDO user = userService.getUser(userId);
        return user != null ? user.getUsername() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.ADMIN;
    }

    @Override
    public AuthLoginRespVO register(AuthRegisterReqVO registerReqVO) {
        // 1. 校验验证码
        validateCaptcha(registerReqVO);

        // 2. 校验用户名是否已存在
        Long userId = userService.registerUser(registerReqVO);

        // 3. 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(userId, registerReqVO.getUsername(), LoginLogTypeEnum.LOGIN_USERNAME);
    }

    @VisibleForTesting
    void validateCaptcha(AuthRegisterReqVO reqVO) {
        boolean response = doValidateCaptcha(reqVO);
        // 验证不通过
        if (!response) {
            throw exception(AUTH_REGISTER_CAPTCHA_CODE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(AuthResetPasswordReqVO reqVO) {
        AdminUserDO userByMobile = userService.getUserByMobile(reqVO.getMobile());
        if (userByMobile == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }

        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO()
                .setCode(reqVO.getCode())
                .setMobile(reqVO.getMobile())
                .setScene(SmsSceneEnum.ADMIN_MEMBER_RESET_PASSWORD.getScene())
                .setUsedIp(getClientIP())
        );

        userService.updateUserPassword(userByMobile.getId(), reqVO.getPassword());
    }
}
