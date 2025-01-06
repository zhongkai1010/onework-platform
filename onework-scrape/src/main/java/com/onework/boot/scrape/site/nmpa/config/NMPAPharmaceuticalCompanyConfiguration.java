package com.onework.boot.scrape.site.nmpa.config;


import com.onework.boot.scrape.site.WebDriverConfiguration;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("onework.scrape.nmpa-pharmaceutical-company")
@Getter
@Setter
public class NMPAPharmaceuticalCompanyConfiguration extends WebDriverConfiguration {

    /**
     *  线程数
     */

    private int threadCount;

    /**
     * 采集链接
     */
    private String url;

    /**
     * 最大页数
     */
    private int maxPage;
}
