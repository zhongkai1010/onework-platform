package com.onework.boot.task.collection.core.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置研究实施地点
 */
@Getter
@Setter
public class CTRRecruitmentLocation {
    private String country;
    private String countryEn;
    private String province;
    private String provinceEn;
    private String city;
    private String cityEn;
    private String institutionHospital;
    private String institutionHospitalEn;
    private String level;
    private String levelEn;
}
