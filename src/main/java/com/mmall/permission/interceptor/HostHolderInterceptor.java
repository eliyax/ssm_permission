package com.mmall.permission.interceptor;

import com.mmall.permission.model.SysUser;
import com.mmall.permission.utils.HostHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HostHolderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SysUser sysUser = (SysUser)request.getSession().getAttribute("user");

        if (sysUser == null) {
            String path = "/signin.jsp";
            response.sendRedirect(path);
            return false;
        }
        HostHolder.addUser(sysUser);
        HostHolder.addRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HostHolder.removeUser();
        HostHolder.removeRequest();
    }
}
