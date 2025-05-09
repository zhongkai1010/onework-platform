package com.onework.boot.module.scraper.task.ctmds.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.ctmds-instrument-file")
@Getter
@Setter
public class CTMDSInstrumentFileConfiguration {
    /**
     * 文件保存路径
     */
    private String savePath;

    /**
     *  线程数
     */

    private int threadCount;
}
