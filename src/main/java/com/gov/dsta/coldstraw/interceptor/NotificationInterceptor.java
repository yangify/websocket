package com.gov.dsta.coldstraw.interceptor;

import com.gov.dsta.coldstraw.validator.parameter.NotificationParameterValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class NotificationInterceptor implements HandlerInterceptor {

    private final NotificationParameterValidator notificationParameterValidator;

    public NotificationInterceptor(NotificationParameterValidator notificationParameterValidator) {
        this.notificationParameterValidator = notificationParameterValidator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {
        notificationParameterValidator.validate(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception)
            throws Exception {
    }
}
