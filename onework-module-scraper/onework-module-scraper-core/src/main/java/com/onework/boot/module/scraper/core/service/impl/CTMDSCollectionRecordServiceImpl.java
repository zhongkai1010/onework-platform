package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.module.scraper.core.dal.mysql.CTMDSCollectionRecordMapper;
import com.onework.boot.module.scraper.core.service.CTMDSCollectionRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床机构信息�?服务实现�?
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Service
@Primary
public class CTMDSCollectionRecordServiceImpl extends ServiceImpl<CTMDSCollectionRecordMapper, CTMDSCollectionRecord> implements CTMDSCollectionRecordService {

}
