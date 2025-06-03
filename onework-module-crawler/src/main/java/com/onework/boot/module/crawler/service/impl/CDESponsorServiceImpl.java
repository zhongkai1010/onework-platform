package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.CDESponsor;
import com.onework.boot.module.crawler.dal.mysql.CDESponsorMapper;
import com.onework.boot.module.crawler.service.CDESponsorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-05-30
 */
@Service
@Primary
public class CDESponsorServiceImpl extends ServiceImpl<CDESponsorMapper, CDESponsor> implements CDESponsorService {

}
