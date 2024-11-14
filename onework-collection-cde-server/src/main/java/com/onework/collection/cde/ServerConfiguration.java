package com.onework.collection.cde;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("onework.collection.cde.server")
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
}
