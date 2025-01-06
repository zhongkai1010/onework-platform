package com.onework.boot.scrape.site.cde.dtos;

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
