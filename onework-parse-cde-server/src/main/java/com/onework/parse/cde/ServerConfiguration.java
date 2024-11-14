package com.onework.parse.cde;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("onework.parse.cde.server")
@Data
@Validated
public class ServerConfiguration {


    /**
     *  线程数
     */

    private int threadCount;


    /**
     * 保存路径
     */

    private String filePath;
}
