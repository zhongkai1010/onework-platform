package com.onework.boot.module.system.dal.mysql.notice;

import com.onework.boot.framework.common.pojo.PageResult;
import com.onework.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.onework.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.onework.boot.module.system.controller.admin.notice.vo.NoticePageReqVO;
import com.onework.boot.module.system.dal.dataobject.notice.NoticeDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeMapper extends BaseMapperX<NoticeDO> {

    default PageResult<NoticeDO> selectPage(NoticePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NoticeDO>()
                .likeIfPresent(NoticeDO::getTitle, reqVO.getTitle())
                .eqIfPresent(NoticeDO::getStatus, reqVO.getStatus())
                .orderByDesc(NoticeDO::getId));
    }

}
