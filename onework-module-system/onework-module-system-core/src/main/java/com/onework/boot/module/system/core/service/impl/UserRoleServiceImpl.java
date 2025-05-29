package com.onework.boot.module.system.core.service.impl;

import com.onework.boot.module.system.core.dal.dataobject.UserRole;
import com.onework.boot.module.system.core.dal.mysql.UserRoleMapper;
import com.onework.boot.module.system.core.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-05-26
 */
@Service
@Primary
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
