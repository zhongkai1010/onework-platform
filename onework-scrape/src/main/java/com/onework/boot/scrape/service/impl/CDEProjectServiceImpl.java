package com.onework.boot.scrape.service.impl;

import com.onework.boot.scrape.dal.dataobject.CDEProject;
import com.onework.boot.scrape.dal.mysql.CDEProjectMapper;
import com.onework.boot.scrape.service.CDEProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Service
@Primary
public class CDEProjectServiceImpl extends ServiceImpl<CDEProjectMapper, CDEProject> implements CDEProjectService {

}
