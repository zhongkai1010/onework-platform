package com.onework.boot.module.scraper.task.cde.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.cde-project-file-parse")
@Getter
@Setter

public class CDEProjectFileParseConfiguration {

    /**
     *  线程数
     */

    private int threadCount;

    /**
     * 保存路径
     */

    private String savePath;
}
