package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.NMPACollectionRecord;
import com.onework.boot.module.crawler.dal.mysql.NMPACollectionRecordMapper;
import com.onework.boot.module.crawler.service.NMPACollectionRecordService;
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
public class NMPACollectionRecordServiceImpl extends ServiceImpl<NMPACollectionRecordMapper, NMPACollectionRecord> implements NMPACollectionRecordService {

}
