package com.onework.boot.scrape.site.cde.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 *  临床试验结果摘要
 */
@Getter
@Setter
public class CDEClinicalTrialResultsSummary {

    private String number;
    private String versionNumber;
    private String versionDate;
}
