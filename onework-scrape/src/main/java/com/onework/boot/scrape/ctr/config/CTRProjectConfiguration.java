package com.onework.boot.scrape.ctr.config;

import com.onework.boot.scrape.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.scrape.ctr-project")
@Getter
@Setter
public class CTRProjectConfiguration extends WebDriverConfiguration {

    /**
     *  采集链接
     */

    private String url;

    /**
     *  线程数
     */

    private int threadCount;

    /**
     *  采集最大页数
     */
    private int maxPage;

    /**
     * 是否重置
     */
    private boolean isReset;
}


