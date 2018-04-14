package com.mmall.permission.utils;

import com.mmall.permission.model.SysUser;

import javax.servlet.http.HttpServletRequest;

public class HostHolder {
    private static ThreadLocal<SysUser> userHolder = new ThreadLocal<>();
    private static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void addUser(SysUser sysUser) {
        userHolder.set(sysUser);

    }

    public static SysUser getUser() {
        return userHolder.get();
    }

    public static void removeUser() {
        userHolder.remove();

    }

    public static void addRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static void removeRequest() {
        requestHolder.remove();
    }


}
