package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.CTRProject;
import com.onework.boot.module.scraper.core.dal.mysql.CTRProjectMapper;
import com.onework.boot.module.scraper.core.service.CTRProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床试验项目�?服务实现�?
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Service
@Primary
public class CTRProjectServiceImpl extends ServiceImpl<CTRProjectMapper, CTRProject> implements CTRProjectService {

}
