package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.CTRCollectionRecord;
import com.onework.boot.module.crawler.dal.mysql.CTRCollectionRecordMapper;
import com.onework.boot.module.crawler.service.CTRCollectionRecordService;
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
public class CTRCollectionRecordServiceImpl extends ServiceImpl<CTRCollectionRecordMapper, CTRCollectionRecord> implements CTRCollectionRecordService {

}
