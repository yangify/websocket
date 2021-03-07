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
        System.out.println("Inside the Post Handle method");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception)
            throws Exception {
        System.out.println("After completion of request and response");
    }


}
//        if (page == null && size == null) return getNotifications();
//        if (page == null) throw new IllegalArgumentException("Page cannot be null when size is provided");
//        if (size == null) throw new IllegalArgumentException("Size cannot be null when page is provided");
