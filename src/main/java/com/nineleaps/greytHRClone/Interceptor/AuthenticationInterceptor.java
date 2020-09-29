package com.nineleaps.greytHRClone.Interceptor;

import com.nineleaps.greytHRClone.exception.UnauthorizedException;
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
        boolean value = false;
        if(request.getMethod().equals("OPTIONS")) return true;
        Cookie userCookie = WebUtils.getCookie(request, "userID");
        if (userCookie == null) {
            throw new UnauthorizedException("unauthorized user");
        } else {
            String retrievedId = userCookie.getValue();
            int id = Integer.parseInt(retrievedId);
            request.setAttribute("id", id);
            value = true;
            return value;

        }
    }
}
