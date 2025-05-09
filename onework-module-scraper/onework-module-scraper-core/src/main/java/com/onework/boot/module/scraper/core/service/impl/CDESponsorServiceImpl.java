package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.CDESponsor;
import com.onework.boot.module.scraper.core.dal.mysql.CDESponsorMapper;
import com.onework.boot.module.scraper.core.service.CDESponsorService;
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
public class CDESponsorServiceImpl extends ServiceImpl<CDESponsorMapper, CDESponsor> implements CDESponsorService {

}
