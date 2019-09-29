package com.tbsinfo.questionlib.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tbsinfo.questionlib.component.RetData;
import com.tbsinfo.questionlib.dao.UserInfoMapper;
import com.tbsinfo.questionlib.model.UserInfo;
import com.tbsinfo.questionlib.service.UserService;
import com.tbsinfo.questionlib.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jayMamba
 * @date 2019/9/25
 * @time 14:41
 * @desc
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByName(String userName) {
        QueryWrapper<UserInfo> wrapper=new QueryWrapper<>();
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName(userName);
        wrapper.setEntity(userInfo);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public RetData login(UserInfo userInfo, HttpSession session) {
        UserInfo userdb=getUserInfoByName(userInfo.getUserName());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(userdb==null) {
            return new RetData().erro("500", "该用户不存在");
        }
        if(userdb.getPassword().equals(Md5Util.md5Password(userInfo.getPassword()))) {
            session.setAttribute("user", userInfo);
            String time=simpleDateFormat.format(new Date());
            log.info("用户："+userInfo.getUserName()+" 于 "+time+" 登录系统");
            return new RetData().success(null);
        }
        return new RetData().erro("500", "密码错误");
    }

    @Override
    public RetData addUser(UserInfo userInfo) {
        UserInfo userdb=getUserInfoByName(userInfo.getUserName());
        if(userdb!=null) {
            return new RetData().erro("500", "该用户名已存在");
        }
        userInfo.setPassword(Md5Util.md5Password(userInfo.getPassword()));
        int res=userInfoMapper.insert(userInfo);
        if(res>0) {
            return new RetData();
        }
        return new RetData().erro("500", "发生未知错误，请稍后重试");
    }
}
