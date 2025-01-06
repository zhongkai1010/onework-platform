package com.onework.boot.scrape.site.cde.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  CDE项目申办方
 */
@Getter
@Setter
public class ProjectSponsor {
    /**
     * 申办方名称
     */
    private String sponsorName;
    /**
     * 联系人姓名
     */
    private String contactPersonName;
    /**
     * 联系人座机
     */
    private String contactLandline;
    /**
     * 联系人手机
     */
    private String contactMobile;
    /**
     * 联系人邮件
     */
    private String contactEmail;
    /**
     * 联系人地址
     */
    private String contactAddress;
    /**
     * 联系人邮政编码
     */
    private String contactPostcode;
    /**
     * CDE项目登记号集合
     */
    private List<String> projects;
}
