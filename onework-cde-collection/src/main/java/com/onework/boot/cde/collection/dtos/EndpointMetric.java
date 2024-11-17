package com.onework.boot.cde.collection.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 终点指标及评价时间
 */
@Getter
@Setter
public class EndpointMetric {

    private String number;
    private String indicator;
    private String evaluationTime;
    private String primaryEndpoint;
}
