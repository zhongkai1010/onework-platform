package com.onework.boot.module.scraper.task.cde.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 伦理委员会信息
 */
@Getter
@Setter
public class CDEEthicsCommittee {

    private String number;
    private String name;
    private String reviewConclusion;
    private String approvalDate;
}
