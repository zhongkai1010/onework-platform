package com.onework.boot.module.scraper.core.service.impl;

import com.onework.boot.module.scraper.core.dal.dataobject.SysLoginRecord;
import com.onework.boot.module.scraper.core.dal.mysql.SysLoginRecordMapper;
import com.onework.boot.module.scraper.core.service.SysLoginRecordService;
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
public class SysLoginRecordServiceImpl extends ServiceImpl<SysLoginRecordMapper, SysLoginRecord> implements SysLoginRecordService {

}
