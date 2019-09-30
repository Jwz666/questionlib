package com.tbsinfo.questionlib.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author jayMamba
 * @date 2019/9/30
 * @time 11:24
 * @desc
 */
public class CKEditorFilter implements Filter {

    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        String url=req.getRequestURI();
        logger.info("request url is "+url);
        if(url.contains("&")) {
            url = url.substring(0, url.indexOf("&"));
            logger.info("after handel url "+url);
            request.getRequestDispatcher(url).forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("========");
    }

    @Override
    public void destroy() {

    }
}
