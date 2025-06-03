package com.onework.boot.module.infra.dal.dataobject.logger;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.apilog.core.enums.OperateTypeEnum;
import com.onework.boot.framework.common.enums.UserTypeEnum;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * API 错误日志
 *
 */
@TableName("infra_api_error_log")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorLogDO extends BaseDO {

    /**
     * {@link #requestParams} 的最大长度
     */
    public static final Integer REQUEST_PARAMS_MAX_LENGTH = 8000;

    /**
     * {@link #resultMsg} 的最大长度
     */
    public static final Integer RESULT_MSG_MAX_LENGTH = 512;

    /**
     * 编号
     */
    @TableId("uid")
    private String uid;
    /**
     * 链路追踪编号
     *
     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 应用名
     *
     * 目前读取 `spring.application.name` 配置项
     */
    private String applicationName;

    // ========== 请求相关字段 ==========

    /**
     * 请求方法名
     */
    private String requestMethod;
    /**
     * 访问地址
     */
    private String requestUrl;
    /**
     * 请求参数
     *
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;
    /**
     * 响应结果
     */
    private String responseBody;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

    // ========== 执行相关字段 ==========

    /**
     * 操作模块
     */
    private String operateModule;
    /**
     * 操作名
     */
    private String operateName;
    /**
     * 操作分类
     *
     * 枚举 {@link OperateTypeEnum}
     */
    private Integer operateType;

    /**
     * 开始请求时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束请求时间
     */
    private LocalDateTime endTime;
    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;

    /**
     * 结果码
     */
    private Integer resultCode;
    /**
     * 结果提示
     */
    private String resultMsg;

    // ========== 异常相关字段 ==========

    /**
     * 异常名
     */
    private String exceptionName;
    /**
     * 异常发生的时间
     */
    private LocalDateTime exceptionTime;
    /**
     * 异常的消息
     */
    private String exceptionMessage;
    /**
     * 异常导致的根消息
     */
    private String exceptionRootCauseMessage;
    /**
     * 异常的栈轨迹
     */
    private String exceptionStackTrace;
    /**
     * 异常发生的类全名
     */
    private String exceptionClassName;
    /**
     * 异常发生的类文件
     */
    private String exceptionFileName;
    /**
     * 异常发生的方法名
     */
    private String exceptionMethodName;
    /**
     * 异常发生的方法所在行
     */
    private Integer exceptionLineNumber;

    // ========== 处理相关字段 ==========

    /**
     * 处理状态
     *
     * 枚举 {@link ApiErrorLogProcessStatusEnum}
     */
    private Integer processStatus;
    /**
     * 处理时间
     */
    private LocalDateTime processTime;
    /**
     * 处理用户编号
     */
    private Long processUserId;

}
