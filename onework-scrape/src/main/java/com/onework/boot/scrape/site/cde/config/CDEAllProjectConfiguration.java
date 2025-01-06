package com.onework.boot.scrape.site.cde.config;

import com.onework.boot.scrape.site.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.scrape.cde-all-project")
@Getter
@Setter
public class CDEAllProjectConfiguration extends WebDriverConfiguration {

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
