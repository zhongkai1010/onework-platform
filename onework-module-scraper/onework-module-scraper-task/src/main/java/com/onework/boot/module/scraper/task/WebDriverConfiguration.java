package com.onework.boot.module.scraper.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebDriverConfiguration {

    /**
     *  是否窗口
     */
    private boolean openWindow;

    /**
     *  是否开启无痕
     */
    private boolean incognito;

    /**
     * 浏览器驱动类型
     */
    private WebDriverType driverType;
}
