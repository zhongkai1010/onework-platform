package com.onework.boot.scrape.cde.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  CDE项目机构
 */
@Getter
@Setter
public class ProjectInstitution {
    /**
     * 机构名称
     */
    private String institutionName;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 地址
     */
    private String address;
    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * CDE项目登记号集合
     */
    private List<String> projects;
}
