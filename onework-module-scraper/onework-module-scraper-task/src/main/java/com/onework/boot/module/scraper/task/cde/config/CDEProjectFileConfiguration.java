package com.onework.boot.module.scraper.task.cde.config;


import com.onework.boot.module.scraper.task.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.cde-project-file")
@Getter
@Setter
public class CDEProjectFileConfiguration extends WebDriverConfiguration {

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
