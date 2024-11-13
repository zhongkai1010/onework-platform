package com.onework.scraper.cde;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.cde.server")
public class ServerConfiguration {

    /**
     *  线程数
     */
    @Data
    private int threadCount;
}
