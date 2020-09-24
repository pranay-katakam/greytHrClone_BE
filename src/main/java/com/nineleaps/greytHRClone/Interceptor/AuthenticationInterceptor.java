package com.nineleaps.greytHRClone.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean  value = false;
        Cookie userCookie = WebUtils.getCookie(request, "userID");
        if (userCookie == null) {
            return value;
        }else{
            String retrievedId=userCookie.getValue();
            int id = Integer.parseInt(retrievedId);
            request.setAttribute("id", id);
            value=true;
            return value;

        }


    }

}
