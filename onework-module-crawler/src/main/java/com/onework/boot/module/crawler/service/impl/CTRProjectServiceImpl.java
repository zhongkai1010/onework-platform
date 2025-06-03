package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.CTRProject;
import com.onework.boot.module.crawler.dal.mysql.CTRProjectMapper;
import com.onework.boot.module.crawler.service.CTRProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床试验项目表 服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-05-30
 */
@Service
@Primary
public class CTRProjectServiceImpl extends ServiceImpl<CTRProjectMapper, CTRProject> implements CTRProjectService {

}
