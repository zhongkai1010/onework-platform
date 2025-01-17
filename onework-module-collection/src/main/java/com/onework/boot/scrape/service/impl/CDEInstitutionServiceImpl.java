package com.onework.boot.scrape.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onework.boot.scrape.dal.dataobject.CDEInstitution;
import com.onework.boot.scrape.dal.mysql.CDEInstitutionMapper;
import com.onework.boot.scrape.service.CDEInstitutionService;
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
public class CDEInstitutionServiceImpl extends ServiceImpl<CDEInstitutionMapper, CDEInstitution> implements CDEInstitutionService {

}
