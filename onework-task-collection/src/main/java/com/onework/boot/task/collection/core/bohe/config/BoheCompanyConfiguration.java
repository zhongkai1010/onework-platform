package com.onework.boot.task.collection.core.bohe.config;

import com.onework.boot.task.collection.core.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.collection.bohe-company")
@Getter
@Setter
public class BoheCompanyConfiguration extends WebDriverConfiguration {

    /**
     *  列表采集链接
     */

    private String url;

    /**
     *  详情采集链接
     */

    private String pageUrl;

    /**
     *  线程数
     */

    private int threadCount;
}
