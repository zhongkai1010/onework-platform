package com.onework.boot.module.crawler.service.impl;

import com.onework.boot.module.crawler.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.module.crawler.dal.mysql.CTMDSCollectionRecordMapper;
import com.onework.boot.module.crawler.service.CTMDSCollectionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床机构信息表 服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-05-30
 */
@Service
@Primary
public class CTMDSCollectionRecordServiceImpl extends ServiceImpl<CTMDSCollectionRecordMapper, CTMDSCollectionRecord> implements CTMDSCollectionRecordService {

}
