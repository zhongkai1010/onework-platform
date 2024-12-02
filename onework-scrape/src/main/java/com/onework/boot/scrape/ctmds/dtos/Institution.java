package com.onework.boot.scrape.ctmds.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Institution {
    private String companyId;        // 公司ID
    private String address;          // 地址
    private String showInfo;         // 显示信息
    private String recordStatus;     // 记录状态
    private String areaName;         // 地区名称
    private String linkTel;          // 联系电话
    private String compName;         // 公司名称
    private String linkMan;          // 联系人
    private Integer row2;            // 行号
    private String recordNo;         // 备案号
}
