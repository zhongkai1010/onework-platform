package com.onework.boot.cde.collection.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 试验人数
 */
@Getter
@Setter
public class ParticipantsNumber {

    private String targetEnrollmentDomestic;
    private String targetEnrollmentForeign;
    private String currentEnrollmentDomestic;
    private String currentEnrollmentForeign;
    private String totalActualEnrollmentDomestic;
    private String totalActualEnrollmentForeign;
}
