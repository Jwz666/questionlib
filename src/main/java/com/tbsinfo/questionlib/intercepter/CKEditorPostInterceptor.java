package com.tbsinfo.questionlib.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jayMamba
 * @date 2019/9/30
 * @time 10:35
 * @desc
 */
@Component
public class CKEditorPostInterceptor implements HandlerInterceptor {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        logger.info("request url is "+url);
        if(url.contains("&")) {
           url = url.substring(0, url.indexOf("&"));
        }
        logger.info("after handel url "+url);
        request.getRequestDispatcher("http://localhost:8081/file/upload").forward(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
