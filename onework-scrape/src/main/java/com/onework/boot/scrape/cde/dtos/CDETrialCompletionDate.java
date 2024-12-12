package com.onework.boot.scrape.cde.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CDETrialCompletionDate {
    private String firstConsentDateDomestic;
    private String firstConsentDateForeign;
    private String firstEnrollmentDateDomestic;
    private String firstEnrollmentDateForeign;
    private String completionDateDomestic;
    private String completionDateForeign;
}
