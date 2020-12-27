package com.example.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class MeasuringInterceptor implements AsyncHandlerInterceptor {
    private final static String REQUEST_TIMESTAMP_KEY = "request.begin.time";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("===================================");
        log.info("============== BEGIN ==============");
        log.info("Interceptor > preHandle");
        log.info("Request : {} {} {}", request.getMethod(), request.getRequestURI(), request.getProtocol());

        request.setAttribute(REQUEST_TIMESTAMP_KEY, System.nanoTime());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  {
        log.info("Interceptor > postHandle");
        log.info("response: {} {}", response.getStatus(), HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
        log.info("modelAndView: {}", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Interceptor > afterCompletion");
        log.info("response: {} {}", response.getStatus(), HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
        log.info("exception: {}", ex);

        long startNanoTime = (long) request.getAttribute(REQUEST_TIMESTAMP_KEY);
        log.info("spent time : {} ms", (System.nanoTime() - startNanoTime) / 1000 / 1000);

        log.info("==============  END  ===============");
        log.info("====================================");
    }
}
