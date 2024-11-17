package com.onework.boot.framework.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.onework.boot.framework.mybatis.core.handler.DefaultDBFieldHandler;
import com.onework.boot.framework.mybatis.core.handler.MyTest;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;


@AutoConfiguration(before = MybatisPlusAutoConfiguration.class)
@MapperScan(value = "${onework.info.base-package}.*.mapper") // Mapper 懒加载，目前仅用于单元测试
public class OneWorkMybatisAutoConfiguration {

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler() {
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }

    @Bean
    public MyTest getMyTest() {
        return new MyTest();
    }
}
