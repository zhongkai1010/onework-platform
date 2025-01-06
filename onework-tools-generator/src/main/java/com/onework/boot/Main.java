package com.onework.boot;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import com.onework.boot.framework.mybatis.core.mapper.BaseMapperX;

import java.nio.file.Paths;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:33061/onework_platform?allowPublicKeyRetrieval=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
        String user = "root";
        String password = "123456";
        String projectPath = Paths.get(System.getProperty("user.dir")) + "\\onework-scrape";
        System.out.println(projectPath);

        FastAutoGenerator.create(url, user, password)
                .globalConfig(builder -> builder
                        .outputDir(projectPath + "\\src\\main\\java")
                        .disableOpenDir()
                )
                .packageConfig(builder -> builder
                        .parent("com.onework.boot") // 设置父包名
                        .moduleName("scrape") // 设置父包模块名
                        .entity("dal.dataobject") // 设置 Entity 包名
                        .service("service") // 设置 Service 包名
                        .serviceImpl("service") // 设置 Service Impl 包名
                        .mapper("dal.mysql") // 设置 Mapper 包名
                        .xml("mapper.xml") // 设置 Mapper XML 包名
                        .controller("controller") //设置 Controller 包名
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "\\src\\main\\resources\\mappers"))
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 模板配置 (TemplateConfig) 自 MyBatis-Plus 3.5.6 版本开始，模板配置已迁移至 StrategyConfig 中。以下是迁移后的配置方式。
                .strategyConfig(builder -> builder
                        .addTablePrefix("ow_") // 增加过滤表前缀
                        .entityBuilder() // 实体策略配置
                        .superClass(BaseDO.class) // 设置父类
                        .javaTemplate("/templates/entity.java") // 设置实体类模板
                        .enableFileOverride() // 覆盖已生成文件
                        .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                        .disableSerialVersionUID() // 禁用生成 serialVersionUID
                        .enableLombok() // 开启 Lombok 模型
                        .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                        .mapperBuilder() // Mapper 策略配置
                        .superClass(BaseMapperX.class) // 设置父类
                        .enableFileOverride() // 覆盖已生成文件
                )

                .execute();
    }
}