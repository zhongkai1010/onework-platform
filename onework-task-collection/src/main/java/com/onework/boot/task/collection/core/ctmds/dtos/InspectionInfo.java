package com.onework.boot.task.collection.core.ctmds.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionInfo {
    /**
     * 检查日期，格式为 yyyy-MM-dd
     */
    private String inspectionDate;
    /**
     * 检查类别，例如：质量检查、安全检查、环境检查等
     */
    private String inspectionCategory;
    /**
     * 监督检查结果，例如：合格、不合格、待整改等
     */
    private String inspectionResult;
    /**
     * 处理情况，例如：已处理、待处理、处理中、未处理等
     */
    private String processingStatus;
}
