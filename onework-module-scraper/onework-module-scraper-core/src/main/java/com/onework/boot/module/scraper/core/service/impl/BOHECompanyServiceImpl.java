package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.BOHECompany;
import com.onework.boot.module.scraper.core.dal.mysql.BOHECompanyMapper;
import com.onework.boot.module.scraper.core.service.BOHECompanyService;
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
public class BOHECompanyServiceImpl extends ServiceImpl<BOHECompanyMapper, BOHECompany> implements BOHECompanyService {

}
