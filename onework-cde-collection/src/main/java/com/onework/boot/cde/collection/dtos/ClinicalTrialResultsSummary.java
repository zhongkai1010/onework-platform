package com.onework.boot.cde.collection.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 *  临床试验结果摘要
 */
@Getter
@Setter
public class ClinicalTrialResultsSummary {

    private String number;
    private String versionNumber;
    private String versionDate;
}
