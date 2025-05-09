package com.onework.boot.module.scraper.task.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置采集人体标本
 */
@Getter
@Setter
public class CTRSample {

    private String sampleName;
    private String sampleNameEn;
    private String tissue;
    private String tissueEn;
    private String fateOfSample;
    private String fateOfSampleEn;
    private String note;
    private String noteEn;
}
