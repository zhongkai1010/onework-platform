package com.onework.boot.task.collection.core.ctmds.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.ctmds-instrument")
@Getter
@Setter
public class CTMDSInstrumentConfiguration {
    /**
     *  采集链接
     */
    private String url;
    /**
     * 文件下载链接
     */
    private String fileUrl;
    /**
     * 文件保存路径
     */
    private String savePath;
    /**
     *  线程数
     */
    private int threadCount;
}
