package com.onework.boot.task.collection.core.ctr.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置试验主办单位
 */
@Getter
@Setter
public class CTRSecondarySponsor {

    private String country;
    private String countryEn;
    private String province;
    private String provinceEn;
    private String city;
    private String cityEn;
    private String institutionHospital;
    private String institutionHospitalEn;
    private String address;
    private String addressEn;
}
