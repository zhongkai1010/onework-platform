package com.onework.boot.module.system.core.service.impl;

import com.onework.boot.module.system.core.dal.dataobject.Role;
import com.onework.boot.module.system.core.dal.mysql.RoleMapper;
import com.onework.boot.module.system.core.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
