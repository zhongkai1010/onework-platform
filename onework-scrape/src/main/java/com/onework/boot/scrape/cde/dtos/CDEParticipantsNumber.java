package com.onework.boot.scrape.cde.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 试验人数
 */
@Getter
@Setter
public class CDEParticipantsNumber {

    private String targetEnrollmentDomestic;
    private String targetEnrollmentForeign;
    private String currentEnrollmentDomestic;
    private String currentEnrollmentForeign;
    private String totalActualEnrollmentDomestic;
    private String totalActualEnrollmentForeign;
}
