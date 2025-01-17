package com.onework.boot.task.collection.core.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置干预措施
 */
@Getter
@Setter
public class CTRIntervention {

    private String group;
    private String groupEn;
    private String sampleSize;
    private String intervention;
    private String interventionEn;
    private String interventionCode;
}
