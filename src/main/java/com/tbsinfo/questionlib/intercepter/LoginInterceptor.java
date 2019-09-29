package com.tbsinfo.questionlib.intercepter;

import com.tbsinfo.questionlib.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jayMamba
 * @date 2019/9/27
 * @time 15:32
 * @desc
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    Logger logger= LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag=true;
        HttpSession session=request.getSession();
        UserInfo userInfo=(UserInfo)session.getAttribute("user");
        if(userInfo==null) {
            logger.info("Interceptor unlogin request");
            response.sendRedirect("/classic/math-signin.html");
            flag=false;
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
