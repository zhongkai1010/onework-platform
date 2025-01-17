package com.onework.boot.scrape.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onework.boot.scrape.dal.dataobject.CTRProject;
import com.onework.boot.scrape.dal.mysql.CTRProjectMapper;
import com.onework.boot.scrape.service.CTRProjectService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床试验项目表 服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Service
@Primary
public class CTRProjectServiceImpl extends ServiceImpl<CTRProjectMapper, CTRProject> implements CTRProjectService {

}
