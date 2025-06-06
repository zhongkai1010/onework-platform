package com.onework.boot.framework.desensitize.core.regex.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.onework.boot.framework.desensitize.core.base.annotation.DesensitizeBy;
import com.onework.boot.framework.desensitize.core.regex.handler.EmailDesensitizationHandler;

import java.lang.annotation.*;

/**
 * 邮箱脱敏注解
 *

 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@DesensitizeBy(handler = EmailDesensitizationHandler.class)
public @interface EmailDesensitize {

    /**
     * 匹配的正则表达式
     */
    String regex() default "(^.)[^@]*(@.*$)";

    /**
     * 替换规则，邮箱;
     *
     * 比如：example@gmail.com 脱敏之后为 e****@gmail.com
     */
    String replacer() default "$1****$2";

    /**
     * 是否禁用脱敏
     *
     * 支持 Spring EL 表达式，如果返回 true 则跳过脱敏
     */
    String disable() default "";

}
