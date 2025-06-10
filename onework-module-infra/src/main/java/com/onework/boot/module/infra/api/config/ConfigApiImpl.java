package com.onework.boot.module.infra.api.config;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 参数配置 API 实现类
 *
 */
@Service
@Validated
public class ConfigApiImpl implements ConfigApi {

    @Override
    public String getConfigValueByKey(String key) {
        return "";
    }
}
