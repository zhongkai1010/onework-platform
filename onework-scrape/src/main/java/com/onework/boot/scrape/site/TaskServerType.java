package com.onework.boot.scrape.site;

import lombok.Getter;

/**
 * 任务类型
 */
@Getter
public enum TaskServerType {

    /**
     * 药物临床试验登记与信息公示平台-全量数据
     */
    CDE_All_PROJECT("药物临床试验登记与信息公示平台-全量数据"),

    /**
     * 药物临床试验登记与信息公示平台-项目列表数据
     */
    CDE_PROJECT("药物临床试验登记与信息公示平台-项目列表数据"),

    /**
     * 药物临床试验登记与信息公示平台-文件下载
     */
    CDE_PROJECT_FILE("药物临床试验登记与信息公示平台-文件下载"),

    /**
     * 药物临床试验登记与信息公示平台-文件解析
     */
    CDE_PROJECT_FILE_PARSE("药物临床试验登记与信息公示平台-文件解析"),

    /**
     *  药物临床试验登记与信息公示平台-数据分析
     */
    CDE_ANALYSIS("药物临床试验登记与信息公示平台-数据分析"),

    /**
     *  药物和医疗器械临床试验机构备案系统-药物备案
     */
    CTMDS_DRUG("药物和医疗器械临床试验机构备案系统-药物备案"),

    /**
     * 药物和医疗器械临床试验机构备案系统-药物备案文件解析
     */
    CTMDS_DRUG_FILE("药物和医疗器械临床试验机构备案系统-药物备案文件解析"),

    /**
     * 药物和医疗器械临床试验机构备案系统-器械备案
     */
    CTMDS_INSTRUMENT("药物和医疗器械临床试验机构备案系统-器械备案"),

    /**
     * 药物和医疗器械临床试验机构备案系统-器械备案文件解析
     */
    CTMDS_INSTRUMENT_FILE("药物和医疗器械临床试验机构备案系统-器械备案文件解析"),

    /**
     * 中国临床试验注册中心-项目
     */
    CTR_PROJECT("中国临床试验注册中心-项目"),

    /**
     * 中国临床试验注册中心-项目文件
     */
    CTR_PROJECT_FILE("中国临床试验注册中心-项目文件"),

    /**
     * 中国临床试验注册中心-项目文件解析
     */
    CTR_PROJECT_FILE_PARSE("中国临床试验注册中心-项目文件解析"),

    /**
     * 国家药品监督管理局-药企生产企业
     */
    NMP_PHARMACEUTICAL_COMPANY("国家药品监督管理局-药企生产企业"),

    /**
     * 复禾健康-药企
     */
    BOHE_COMPANY("复禾健康-药企");


    private final String description;

    // 构造函数
    TaskServerType(String description) {
        this.description = description;
    }
}

