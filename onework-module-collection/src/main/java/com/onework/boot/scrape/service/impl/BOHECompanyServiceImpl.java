package com.onework.boot.scrape.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onework.boot.scrape.dal.dataobject.BOHECompany;
import com.onework.boot.scrape.dal.mysql.BOHECompanyMapper;
import com.onework.boot.scrape.service.BOHECompanyService;
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
public class BOHECompanyServiceImpl extends ServiceImpl<BOHECompanyMapper, BOHECompany> implements BOHECompanyService {

}
