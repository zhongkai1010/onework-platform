package com.onework.boot.module.scraper.task.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置测量指标
 */
@Getter
@Setter
public class CTROutcome {

    private String outcome;
    private String outcomeEn;
    private String type;
    private String typeEn;
    private String measureTimePoint;
    private String measureTimePointEn;
    private String measureMethod;
    private String measureMethodEn;
}
