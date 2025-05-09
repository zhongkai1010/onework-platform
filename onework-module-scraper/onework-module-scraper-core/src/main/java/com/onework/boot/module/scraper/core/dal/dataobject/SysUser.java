package com.onework.boot.module.scraper.core.dal.dataobject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.onework.boot.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author onework
 * @since 2025-05-09
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUser extends BaseDO {

    @TableId("uid")
    private String uid;

    /**
     * 账号
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别, 字典标识
     */
    @TableField("sex")
    private String sex;

    /**
     * 性别名称
     */
    @TableField("sex_name")
    private String sexName;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 手机�?
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 邮箱是否验证, 0�? 1�?
     */
    @TableField("email_verified")
    private Byte emailVerified;

    /**
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;

    /**
     * 个人简�?
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 机构id
     */
    @TableField("organization_id")
    private String organizationId;

    /**
     * 机构名称
     */
    @TableField("organization_name")
    private String organizationName;

    /**
     * 状�? 0正常, 1冻结
     */
    @TableField("status")
    private Byte status;
}
