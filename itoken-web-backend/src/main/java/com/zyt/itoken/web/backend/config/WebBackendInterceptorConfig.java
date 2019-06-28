package com.zyt.itoken.web.backend.config;

import com.zyt.itoken.web.backend.interceptor.WebBackendInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebBackendInterceptorConfig implements WebMvcConfigurer {

    @Bean
    WebBackendInterceptor webBackendInterceptor() {
        return new WebBackendInterceptor ();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor (webBackendInterceptor ())
                .addPathPatterns ("/**").excludePathPatterns ("/static");
    }
}
