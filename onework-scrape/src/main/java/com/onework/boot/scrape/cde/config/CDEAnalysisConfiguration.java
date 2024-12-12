package com.onework.boot.scrape.cde.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.scrape.cde-analysis")
@Getter
@Setter

public class CDEAnalysisConfiguration {

    /**
     *  线程数
     */

    private int threadCount;
}
