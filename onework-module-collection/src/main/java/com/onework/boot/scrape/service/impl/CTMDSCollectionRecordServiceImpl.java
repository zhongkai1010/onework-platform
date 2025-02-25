package com.onework.boot.scrape.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onework.boot.scrape.dal.dataobject.CTMDSCollectionRecord;
import com.onework.boot.scrape.dal.mysql.CTMDSCollectionRecordMapper;
import com.onework.boot.scrape.service.CTMDSCollectionRecordService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 临床机构信息表 服务实现类
 * </p>
 *
 * @author onework
 * @since 2025-01-13
 */
@Service
@Primary
public class CTMDSCollectionRecordServiceImpl extends ServiceImpl<CTMDSCollectionRecordMapper, CTMDSCollectionRecord> implements CTMDSCollectionRecordService {

}
