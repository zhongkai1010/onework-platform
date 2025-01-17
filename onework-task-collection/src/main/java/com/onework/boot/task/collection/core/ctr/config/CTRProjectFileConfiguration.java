package com.onework.boot.task.collection.core.ctr.config;

import com.onework.boot.task.collection.core.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.ctr-project-file")
@Getter
@Setter
public class CTRProjectFileConfiguration extends WebDriverConfiguration {

    /**
     *  采集链接
     */

    private String url;

    /**
     *  线程数
     */

    private int threadCount;

    /**
     * 保存路径
     */
    private String savePath;
}
