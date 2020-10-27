package com.nineleaps.greytHRClone.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class Interceptor implements WebMvcConfigurer {


    private AuthenticationInterceptor authenticationInterceptor;
    private UserInterceptor userInterceptor;

    @Autowired
    public Interceptor(AuthenticationInterceptor authenticationInterceptor, UserInterceptor userInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.userInterceptor = userInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout",
                        "/swagger-ui/**","/v3/api-docs",
                        "/employee","/designations","/departments","/designation","/department")
                .order(1);

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/user")
                .order(2);


    }
}

