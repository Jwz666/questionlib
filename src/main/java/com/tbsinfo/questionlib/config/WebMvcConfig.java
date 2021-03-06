package com.tbsinfo.questionlib.config;

import com.tbsinfo.questionlib.intercepter.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jayMamba
 * @date 2019/9/27
 * @time 15:35
 * @desc
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/login","/file/**","/uploadWord","/assets/**","/classic/math-signin.html","config/**","/lib/**");
    }
}
