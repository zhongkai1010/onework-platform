package com.onework.boot.scrape;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("onework.scrape")
@Data
@Validated
public class ServerConfiguration {

    /**
     *  线程数
     */

    private int threadCount;

    /**
     *  驱动路径
     */

    private String drivePath;

    /**
     *  最大错误次数
     */

    private int maxErrorCount;

    /**
     *  是否窗口
     */
    private boolean openWindow;

    /**
     *  是否开启无痕
     */
    private boolean incognito;

    /**
     *  采集最大页数
     */
    private int maxPage;

    /**
     *  采集链接
     */

    private String cdeCollectionUrl;

    /**
     * 保存路径
     */

    private String cdeSavePath;

    /**
     *  采集链接
     */

    private String ctrCollectionUrl;

    /**
     * 保存路径
     */

    private String ctrSavePath;

    /**
     *  采集链接
     */

    private String ctmdsCollectionUrl;

    /**
     * 保存路径
     */

    private String ctmdsSavePath;
}
