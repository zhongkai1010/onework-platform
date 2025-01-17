package com.onework.boot.task.collection.core.cde.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 参加机构信息
 */
@Getter
@Setter
public class CDEClinicalInstitution {
    /**
     *  序号
     */
    private String number;
    /**
     * 机构名称
     */
    private String organizationName;
    /**
     * 研究者
     */
    private String principal;
    /**
     * 注册国家
     */
    private String region;
    /**
     * 注册省份
     */
    private String province;
    /**
     * 注册城市
     */
    private String city;

}
