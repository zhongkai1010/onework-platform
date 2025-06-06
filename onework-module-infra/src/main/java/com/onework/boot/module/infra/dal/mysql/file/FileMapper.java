package com.onework.boot.module.infra.dal.mysql.file;

import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.module.infra.controller.admin.file.vo.file.FilePageReqVO;
import com.onework.boot.module.infra.dal.dataobject.file.FileDO;
import org.apache.ibatis.annotations.Mapper;
import com.onework.boot.framework.mybatis.core.mapper.BaseMapperX;
/**
 * 文件操作 Mapper
 *
 */
@Mapper
public interface FileMapper extends BaseMapperX<FileDO> {

    default PageResult<FileDO> selectPage(FilePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileDO>()
                .likeIfPresent(FileDO::getPath, reqVO.getPath())
                .likeIfPresent(FileDO::getType, reqVO.getType())
                .betweenIfPresent(FileDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileDO::getId));
    }

}
