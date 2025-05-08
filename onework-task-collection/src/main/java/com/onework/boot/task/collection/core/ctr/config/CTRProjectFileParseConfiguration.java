package com.onework.boot.task.collection.core.ctr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.ctr-project-file-parse")
@Getter
@Setter
public class CTRProjectFileParseConfiguration {

    /**
     *  线程数
     */

    private int threadCount;

    /**
     * 保存路径
     */
    private String savePath;
}
