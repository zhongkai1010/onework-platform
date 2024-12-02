package com.onework.boot;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
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
                        .parent("com.onework.boot.scrape.data")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "\\src\\main\\resources\\mappers"))
                )
                .strategyConfig(builder -> builder
                        .addTablePrefix("ow_")
                        .serviceBuilder()
                        .disable()
                        .convertServiceFileName(fileName -> {
                            // 自定义文件名转换规则：以 CDE 开头
                            if (fileName.toLowerCase().startsWith("cde")) {
                                return "CDE" + fileName.substring(3) + "Service";
                            }
                            if (fileName.toLowerCase().startsWith("ctr")) {
                                return "CTR" + fileName.substring(3) + "Service";
                            }
                            if (fileName.toLowerCase().startsWith("ctmds")) {
                                return "CTMDS" + fileName.substring(5) + "Service";
                            }
                            return fileName;
                        })
                        .convertServiceImplFileName(fileName -> {
                            // 自定义文件名转换规则：以 CDE 开头
                            if (fileName.toLowerCase().startsWith("cde")) {
                                return "CDE" + fileName.substring(3) + "ServiceImpl";
                            }
                            if (fileName.toLowerCase().startsWith("ctr")) {
                                return "CTR" + fileName.substring(3) + "ServiceImpl";
                            }
                            if (fileName.toLowerCase().startsWith("ctmds")) {
                                return "CTMDS" + fileName.substring(5) + "ServiceImpl";
                            }
                            return fileName;
                        })
                        .enableFileOverride()
                        .mapperBuilder()
                        .superClass(BaseMapperX.class)
                        .superClass("com.onework.boot.framework.mybatis.core.mapper.BaseMapperX")
                        .convertMapperFileName(fileName -> {
                            // 自定义文件名转换规则：以 CDE 开头
                            if (fileName.toLowerCase().startsWith("cde")) {
                                return "CDE" + fileName.substring(3) + "Mapper";
                            }
                            if (fileName.toLowerCase().startsWith("ctr")) {
                                return "CTR" + fileName.substring(3) + "Mapper";
                            }
                            if (fileName.toLowerCase().startsWith("ctmds")) {
                                return "CTMDS" + fileName.substring(5) + "Mapper";
                            }
                            return fileName;
                        })
                        .enableFileOverride()
                        .convertXmlFileName(fileName -> {
                            // 自定义文件名转换规则：以 CDE 开头
                            if (fileName.toLowerCase().startsWith("cde")) {
                                return "CDE" + fileName.substring(3) + "Mapper";
                            }
                            if (fileName.toLowerCase().startsWith("ctr")) {
                                return "CTR" + fileName.substring(3) + "Mapper";
                            }
                            if (fileName.toLowerCase().startsWith("ctmds")) {
                                return "CTMDS" + fileName.substring(5) + "Mapper";
                            }
                            return fileName;
                        })
                        .entityBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .convertFileName(fileName -> {
                            // 自定义文件名转换规则：以 CDE 开头
                            if (fileName.toLowerCase().startsWith("cde")) {
                                return "CDE" + fileName.substring(3);
                            }
                            if (fileName.toLowerCase().startsWith("ctr")) {
                                return "CTR" + fileName.substring(3);
                            }
                            if (fileName.toLowerCase().startsWith("ctmds")) {
                                return "CTMDS" + fileName.substring(5) ;
                            }
                            return fileName;
                        })
                        .enableLombok()
                        .enableFileOverride()
                        .addTableFills(new Column("created_at", FieldFill.INSERT))
                        .addTableFills(new Column("updated_at", FieldFill.INSERT_UPDATE))
                        .enableTableFieldAnnotation()
                        .logicDeleteColumnName("deleted")
                        .controllerBuilder()
                        .disable()
                        .mapperBuilder().disableMapperXml()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}