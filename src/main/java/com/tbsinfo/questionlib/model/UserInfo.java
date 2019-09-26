package com.tbsinfo.questionlib.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jayMamba
 * @date 2019/9/25
 * @time 14:29
 * @desc
 */
@Data
@TableName("quelib_user_info")
public class UserInfo {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
}
