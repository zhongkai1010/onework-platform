package com.onework.boot.scrape.site.cde.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 终点指标及评价时间
 */
@Getter
@Setter
public class CDEEndpointMetric {

    private String number;
    private String indicator;
    private String evaluationTime;
    private String primaryEndpoint;
}
