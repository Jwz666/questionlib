package com.tbsinfo.questionlib.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.model.UserInfo;

import javax.servlet.http.HttpSession;


/**
 * @author jayMamba
 * @date 2019/9/25
 * @time 14:32
 * @desc
 */

public interface UserService extends IService<UserInfo> {

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    UserInfo getUserInfoByName(String userName);

    /**
     * 登录处理
     * @param userInfo
     * @return
     */
    RetData login(UserInfo userInfo, HttpSession session);

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    RetData addUser(UserInfo userInfo);

}
