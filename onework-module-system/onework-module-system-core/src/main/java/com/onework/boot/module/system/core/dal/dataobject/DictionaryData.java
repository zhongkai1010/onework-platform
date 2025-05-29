package com.onework.boot.module.system.core.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author onework
 * @since 2025-05-26
 */
@Getter
@Setter
@TableName("sys_dictionary_data")
public class DictionaryData extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 字典id
     */
    @TableField("dict_id")
    private String dictId;

    /**
     * 字典代码
     */
    @TableField("dict_code")
    private String dictCode;

    /**
     * 字典数据标识
     */
    @TableField("dict_data_code")
    private String dictDataCode;

    /**
     * 字典数据名称
     */
    @TableField("dict_data_name")
    private String dictDataName;

    /**
     * 字典名称
     */
    @TableField("dict_name")
    private String dictName;

    /**
     * 备注
     */
    @TableField("comments")
    private String comments;

    /**
     * 排序号
     */
    @TableField("sort_number")
    private Integer sortNumber;
}
