package com.onework.boot.cde.collection.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrialCompletionDate {
    private String firstConsentDateDomestic;
    private String firstConsentDateForeign;
    private String firstEnrollmentDateDomestic;
    private String firstEnrollmentDateForeign;
    private String completionDateDomestic;
    private String completionDateForeign;
}
