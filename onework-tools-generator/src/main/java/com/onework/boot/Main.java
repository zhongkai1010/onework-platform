package com.onework.boot;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import com.onework.boot.framework.mybatis.core.mapper.BaseMapperX;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:33061/onework_platform?allowPublicKeyRetrieval=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false";
        String user = "root";
        String password = "123456";
        String projectPath = Paths.get(System.getProperty("user.dir")) + "\\onework-module-scraper\\onework-module-scraper-core";
        System.out.println(projectPath);

        FastAutoGenerator.create(url, user, password)
                .globalConfig(builder -> builder
                        .outputDir(projectPath + "\\src\\main\\java") // 指定代码生成的输出目录
                        .author("onework") // 置作者名
                        // .enableSwagger() // 开启 Swagger 模式
                        //.enableSpringdoc()
                        .disableOpenDir())
                .packageConfig(builder -> builder
                        .parent("com.onework.boot.module.scraper.core") // 设置父包名
                        //.moduleName("scrape") // 设置父包模块名
                        .entity("dal.dataobject") // 设置 Entity 包名
                        .service("service") // 设置 Service 包名
                        .serviceImpl("service.impl") // 设置 Service Impl 包名
                        .mapper("dal.mysql") // 设置 Mapper 包名
                        .xml("mapper.xml") // 设置 Mapper XML 包名
                        //.controller("controller") //设置 Controller 包名
                        .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "\\src\\main\\resources\\mappers"))
                )

                // 模板配置 (TemplateConfig) 自 MyBatis-Plus 3.5.6 版本开始，模板配置已迁移至 StrategyConfig 中。以下是迁移后的配置方式。
                .strategyConfig(builder -> builder
                        // .enableCapitalMode() // 开启大写命名
                        .addTablePrefix("ow_") // 增加过滤表前缀
                        .likeTable(new LikeTable("ow_")) // 模糊匹配表名
                ).strategyConfig(builder -> builder
                        .entityBuilder() // 实体策略配置
                        //.idType(IdType.AUTO)
                        //.disable()
                        .enableFileOverride() // 覆盖已生成文件
                        .superClass(BaseDO.class) // 设置父类
                        .addIgnoreColumns("creator", "updater", "deleted", "create_time", "update_time") // 添加忽略字段
                        .javaTemplate("/templates/entity.java") // 设置实体类模板
                        .enableFileOverride() // 覆盖已生成文件
                        .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                     .convertFileName(fileName -> convertFileName(fileName, ""))
                        .disableSerialVersionUID() // 禁用生成 serialVersionUID
                        .enableLombok() // 开启 Lombok 模型
                        .enableTableFieldAnnotation() // 开启生成实体时生成字段注解
                ).strategyConfig(builder -> builder
                        .mapperBuilder()
                        //.disable()
                        .superClass(BaseMapperX.class) // 设置父类
                        .enableFileOverride()
                      .convertMapperFileName(fileName -> convertFileName(fileName, "Mapper"))
                      .convertXmlFileName(fileName -> convertFileName(fileName, "Mapper"))
                )
                .strategyConfig(builder -> builder
                        .serviceBuilder()
                        //.disable()
                        .enableFileOverride()
                        .convertServiceFileName(fileName -> convertFileName(fileName, "Service"))
                      .convertServiceImplFileName(fileName -> convertFileName(fileName, "ServiceImpl"))
                )
                .strategyConfig(builder -> builder
                        .controllerBuilder()
                        //.disable()
                        .enableFileOverride()
                        .enableHyphenStyle() // 开启驼峰转连字符
                   .convertFileName(fileName -> convertFileName(fileName, "Controller"))
                        .enableRestStyle() // 开启生成@RestController 控制器
                )
                .injectionConfig(builder ->
                        builder.beforeOutputFile((tableInfo, stringObjectMap) -> {
                            customControllerMapping(tableInfo.getEntityName(), stringObjectMap);
                        })
                )
                .injectionConfig(builder -> {
                    CustomFile.Builder queryVoCustomFileBuilder = new CustomFile.Builder();
                    CustomFile queryVoCustomFile = queryVoCustomFileBuilder
                            .fileName("PageReqVO.java")
                            .templatePath("/templates/entityQueryPageVO.java.vm")
                            .packageName("controller.vo")
                            .enableFileOverride()
                            .build();
                    builder.customFile(queryVoCustomFile);
                })
                .injectionConfig(builder -> {
                    CustomFile.Builder queryVoCustomFileBuilder = new CustomFile.Builder();
                    CustomFile queryVoCustomFile = queryVoCustomFileBuilder
                            .fileName("ReqVO.java")
                            .templatePath("/templates/entityQueryVO.java.vm")
                            .packageName("controller.vo")
                            .enableFileOverride()
                            .build();
                    builder.customFile(queryVoCustomFile);
                })
                .templateEngine(new CustomVelocityTemplateEngine())
                .execute();
    }

    private static void customControllerMapping(String entityName, Map<String, Object> stringObjectMap) {
        switch (entityName) {
            case "BOHECompany":
                stringObjectMap.replace("controllerMappingHyphen", "bohe-company");
                break;
            case "CDECollectionRecord":
                stringObjectMap.replace("controllerMappingHyphen", "cde-collection-record");
                break;
            case "CDEInstitution":
                stringObjectMap.replace("controllerMappingHyphen", "cde-institution");
                break;
            case "CDEProject":
                stringObjectMap.replace("controllerMappingHyphen", "cde-project");
                break;
            case "CDEResearcher":
                stringObjectMap.replace("controllerMappingHyphen", "cde-researcher");
                break;
            case "CDESponsor":
                stringObjectMap.replace("controllerMappingHyphen", "cde-sponsor");
                break;
            case "CTRProject":
                stringObjectMap.replace("controllerMappingHyphen", "ctr-project");
                break;
            case "CTMDSCollectionRecord":
                stringObjectMap.replace("controllerMappingHyphen", "ctmds-collection-record");
                break;
            case "CTRCollectionRecord":
                stringObjectMap.replace("controllerMappingHyphen", "ctr-collection-record");
                break;
            case "NMPACollectionRecord":
                stringObjectMap.replace("controllerMappingHyphen", "nmpa-collection-record");
                break;
        }
    }

    private static String convertFileName(String fileName, String suffix) {
        String newFileName = fileName;
        // 自定义文件名转换规则：以 CDE 开头
        if (fileName.toLowerCase().startsWith("cde")) {
            newFileName = "CDE" + fileName.substring(3);
        }
        if (fileName.toLowerCase().startsWith("ctr")) {
            newFileName = "CTR" + fileName.substring(3);
        }
        if (fileName.toLowerCase().startsWith("ctmds")) {
            newFileName = "CTMDS" + fileName.substring(5);
        }
        if (fileName.toLowerCase().startsWith("nmpa")) {
            newFileName = "NMPA" + fileName.substring(4);
        }
        if (fileName.toLowerCase().startsWith("bohe")) {
            newFileName = "BOHE" + fileName.substring(4);
        }
        return newFileName + suffix;
    }
}