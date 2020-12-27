package com.example.demo.config;

import com.example.demo.interceptor.MeasuringInterceptor;
import com.example.demo.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    MeasuringInterceptor measuringInterceptor;
    SecurityInterceptor securityInterceptor;

    @Autowired
    public WebMvcConfig(MeasuringInterceptor measuringInterceptor, SecurityInterceptor securityInterceptor) {
        this.measuringInterceptor = measuringInterceptor;
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/security/**", "/users/**")
                .excludePathPatterns("/security/generate/token");
        registry.addInterceptor(measuringInterceptor)
                .addPathPatterns("/**");
    }

}
