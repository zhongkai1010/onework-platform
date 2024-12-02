package com.onework.boot.scrape.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Outcome {

    private String outcome;
    private String outcomeEn;
    private String type;
    private String typeEn;
    private String measureTimePoint;
    private String measureTimePointEn;
    private String measureMethod;
    private String measureMethodEn;
}
