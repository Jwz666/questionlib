package com.tbsinfo.questionlib.config;

import com.tbsinfo.questionlib.filter.CKEditorFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jayMamba
 * @date 2019/9/30
 * @time 11:35
 * @desc
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CKEditorFilter());
        registration.addUrlPatterns("/file/*");
        registration.setName("CKEditorFilter");
        registration.setOrder(1);
        return registration;
    }
}
