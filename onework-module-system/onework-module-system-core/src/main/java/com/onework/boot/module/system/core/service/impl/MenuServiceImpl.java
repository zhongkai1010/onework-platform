package com.onework.boot.module.system.core.service.impl;

import com.onework.boot.module.system.core.dal.dataobject.Menu;
import com.onework.boot.module.system.core.dal.mysql.MenuMapper;
import com.onework.boot.module.system.core.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
