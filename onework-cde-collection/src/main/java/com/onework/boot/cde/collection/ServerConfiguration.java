package com.onework.boot.cde.collection;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("onework.cde.collection")
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
     * 保存路径
     */

    private String savePath;

    /**
     *  最大错误次数
     */

    private int maxErrorCount;

    /**
     *  采集链接
     */

    private String collectionUrl;

    /**
     *  是否初次加载数据
     */
    private boolean initData;

    /**
     *  是否窗口
     */
    private boolean openWindow;

    /**
     *  是否开启无痕
     */
    private boolean incognito;

    /**
     *  采集类型
     */
    private CollectionServerType serverType;

    /**
     *  采集最大页数
     */
    private int maxPage;
}
