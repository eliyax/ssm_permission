package com.mmall.permission.interceptor;

import com.mmall.permission.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor implements HandlerInterceptor {
    private static final String START_TIME = "requestStarTime";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(parameterMap));
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        String url = request.getRequestURI();
        Map parameterMap = request.getParameterMap();
        log.info("request end. url:{}, params:{},cost:{}", url, JsonMapper.obj2String(parameterMap),(end - start));
    }
}
