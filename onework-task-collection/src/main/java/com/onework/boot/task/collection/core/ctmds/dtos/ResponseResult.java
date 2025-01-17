package com.onework.boot.task.collection.core.ctmds.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseResult {
    private List<Institution> data; // 数据列表
    private boolean success;                        // 操作是否成功
    private String curPage;                         // 当前页码
    private int totalRows;
}
