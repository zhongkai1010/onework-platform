package com.onework.boot.module.infra.framework.security.permission;

import com.onework.boot.framework.common.api.permission.PermissionCommonApi;
import com.onework.boot.framework.common.api.permission.dto.DeptDataPermissionRespDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 权限 API 实现类
 */
@Service
public class PermissionCommonApiImpl implements PermissionCommonApi {

    // 模拟一些权限数据
    private static final Set<String> ADMIN_PERMISSIONS = new HashSet<>(Arrays.asList(
            "system:user:create",
            "system:user:update",
            "system:user:delete",
            "system:user:query"
    ));

    private static final Set<String> ADMIN_ROLES = new HashSet<>(Arrays.asList(
            "admin",
            "super_admin"
    ));

    @Override
    public boolean hasAnyPermissions(Long userId, String... permissions) {
        // 默认返回 true，表示有权限
        return true;
    }

    @Override
    public boolean hasAnyRoles(Long userId, String... roles) {
        // 默认返回 true，表示有角色
        return true;
    }

    @Override
    public DeptDataPermissionRespDTO getDeptDataPermission(Long userId) {
        // 返回默认的部门数据权限
        DeptDataPermissionRespDTO respDTO = new DeptDataPermissionRespDTO();
        respDTO.setAll(true); // 默认可以查看所有部门
        return respDTO;
    }
} 