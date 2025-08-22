package com.onework.boot.module.system.convert.auth;

import com.onework.boot.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import com.onework.boot.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.onework.boot.module.system.controller.admin.auth.vo.AuthPermissionInfoRespVO;
import com.onework.boot.module.system.controller.admin.auth.vo.AuthSmsLoginReqVO;
import com.onework.boot.module.system.controller.admin.auth.vo.AuthSmsSendReqVO;
import com.onework.boot.module.system.dal.dataobject.permission.MenuDO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-22T09:26:24+0800",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
public class AuthConvertImpl implements AuthConvert {

    @Override
    public AuthPermissionInfoRespVO.MenuVO convertTreeNode(MenuDO menu) {
        if ( menu == null ) {
            return null;
        }

        AuthPermissionInfoRespVO.MenuVO.MenuVOBuilder menuVO = AuthPermissionInfoRespVO.MenuVO.builder();

        menuVO.id( menu.getId() );
        menuVO.parentId( menu.getParentId() );
        menuVO.name( menu.getName() );
        menuVO.path( menu.getPath() );
        menuVO.component( menu.getComponent() );
        menuVO.componentName( menu.getComponentName() );
        menuVO.icon( menu.getIcon() );
        menuVO.visible( menu.getVisible() );
        menuVO.keepAlive( menu.getKeepAlive() );
        menuVO.alwaysShow( menu.getAlwaysShow() );

        return menuVO.build();
    }

    @Override
    public SmsCodeSendReqDTO convert(AuthSmsSendReqVO reqVO) {
        if ( reqVO == null ) {
            return null;
        }

        SmsCodeSendReqDTO smsCodeSendReqDTO = new SmsCodeSendReqDTO();

        smsCodeSendReqDTO.setMobile( reqVO.getMobile() );
        smsCodeSendReqDTO.setScene( reqVO.getScene() );

        return smsCodeSendReqDTO;
    }

    @Override
    public SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp) {
        if ( reqVO == null && scene == null && usedIp == null ) {
            return null;
        }

        SmsCodeUseReqDTO smsCodeUseReqDTO = new SmsCodeUseReqDTO();

        if ( reqVO != null ) {
            smsCodeUseReqDTO.setMobile( reqVO.getMobile() );
            smsCodeUseReqDTO.setCode( reqVO.getCode() );
        }
        smsCodeUseReqDTO.setScene( scene );
        smsCodeUseReqDTO.setUsedIp( usedIp );

        return smsCodeUseReqDTO;
    }
}
