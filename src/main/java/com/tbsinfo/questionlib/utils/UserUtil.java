package com.tbsinfo.questionlib.utils;

import com.tbsinfo.questionlib.model.UserInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUtil
{
    public static UserInfo getCurrentUser()
    {
        Object o=RequestUtil.getRequest().getSession().getAttribute("user");
        if (o==null) log.warn(RequestUtil.getRequestHost()+"当前无有效用户，可能导致异常");
        return (UserInfo) o;
    }

}