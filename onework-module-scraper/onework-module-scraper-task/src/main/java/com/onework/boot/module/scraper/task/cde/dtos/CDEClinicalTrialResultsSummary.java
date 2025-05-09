package com.onework.boot.module.scraper.task.cde.dtos;

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
