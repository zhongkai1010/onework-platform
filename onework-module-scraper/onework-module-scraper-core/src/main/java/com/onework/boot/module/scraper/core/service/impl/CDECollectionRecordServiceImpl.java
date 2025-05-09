package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.CDECollectionRecord;
import com.onework.boot.module.scraper.core.dal.mysql.CDECollectionRecordMapper;
import com.onework.boot.module.scraper.core.service.CDECollectionRecordService;
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
public class CDECollectionRecordServiceImpl extends ServiceImpl<CDECollectionRecordMapper, CDECollectionRecord> implements CDECollectionRecordService {

}
