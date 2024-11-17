package com.onework.boot.cde.collection.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 伦理委员会信息
 */
@Getter
@Setter
public class EthicsCommittee {

    private String number;
    private String name;
    private String reviewConclusion;
    private String approvalDate;
}
