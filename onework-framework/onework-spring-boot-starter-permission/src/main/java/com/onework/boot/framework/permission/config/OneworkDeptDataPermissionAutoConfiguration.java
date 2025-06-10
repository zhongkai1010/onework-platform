package com.onework.boot.framework.permission.config;

import com.onework.boot.framework.common.api.permission.PermissionCommonApi;
import com.onework.boot.framework.permission.core.rule.dept.DeptDataPermissionRule;
import com.onework.boot.framework.permission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.onework.boot.framework.security.LoginUser;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 基于部门的数据权限 AutoConfiguration
 *
 * 
 */
@AutoConfiguration
@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {PermissionCommonApi.class, DeptDataPermissionRuleCustomizer.class})
public class OneworkDeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(PermissionCommonApi permissionApi,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {
        // 创建 DeptDataPermissionRule 对象
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
        // 补全表配置
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
