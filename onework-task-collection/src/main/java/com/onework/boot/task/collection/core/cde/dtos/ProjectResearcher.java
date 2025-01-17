package com.onework.boot.task.collection.core.cde.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * CDE项目研究者
 */
@Getter
@Setter
public class ProjectResearcher {
    /**
     *  研究者名称
     */
    private String researcherName;
    /**
     * 机构名称
     */
    private String institutionName;
    /**
     * 学历
     */
    private String degree;
    /**
     * 职称
     */
    private String title;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * CDE项目登记号集合
     */
    private List<String> projects;
}
