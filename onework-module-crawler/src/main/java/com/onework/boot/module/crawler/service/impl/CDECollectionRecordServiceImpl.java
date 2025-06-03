package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.CDECollectionRecord;
import com.onework.boot.module.crawler.dal.mysql.CDECollectionRecordMapper;
import com.onework.boot.module.crawler.service.CDECollectionRecordService;
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
public class CDECollectionRecordServiceImpl extends ServiceImpl<CDECollectionRecordMapper, CDECollectionRecord> implements CDECollectionRecordService {

}
