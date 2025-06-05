package com.onework.boot.module.infra.dal.mysql.db;

import com.onework.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.onework.boot.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
