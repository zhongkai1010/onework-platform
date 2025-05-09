package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.SysOperationRecord;
import com.onework.boot.module.scraper.core.dal.mysql.SysOperationRecordMapper;
import com.onework.boot.module.scraper.core.service.SysOperationRecordService;
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
public class SysOperationRecordServiceImpl extends ServiceImpl<SysOperationRecordMapper, SysOperationRecord> implements SysOperationRecordService {

}
