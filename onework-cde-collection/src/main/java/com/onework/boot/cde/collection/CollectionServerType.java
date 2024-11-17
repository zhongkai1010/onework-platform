package com.onework.boot.cde.collection;

public enum CollectionServerType {

    /**
     * 检索所有CDE项目文库
     */
    ALL,

    /**
     * 下载所有文件
     */
    FILE_DOWNLOAD,

    /**
     *  文件解析
     */
    FILE_PARSE,

    /**
     *  检索所有列表
     */
    SCAN_LIST,

    /**
     * 检索最新列表，下载文件，解析文件
     */
    SCAN_AND_FILE_DOWNLOAD_AND_FILE_PARSE
}
