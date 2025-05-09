package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.CDEResearcher;
import com.onework.boot.module.scraper.core.dal.mysql.CDEResearcherMapper;
import com.onework.boot.module.scraper.core.service.CDEResearcherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现�?
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Service
@Primary
public class CDEResearcherServiceImpl extends ServiceImpl<CDEResearcherMapper, CDEResearcher> implements CDEResearcherService {

}
