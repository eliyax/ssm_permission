package com.mmall.permission.utils;

import com.mmall.permission.VO.JsonData;

public class JsonDataUtil {

    public static JsonData success() {
        JsonData jsonData = new JsonData();
        jsonData.setRet(true);
        return jsonData;
    }

    public static JsonData success(Object object) {
        JsonData jsonData = new JsonData();
        jsonData.setRet(true);
        jsonData.setData(object);
        return jsonData;
    }

    public static JsonData success(Object object,String msg) {
        JsonData jsonData = new JsonData();
        jsonData.setRet(true);
        jsonData.setData(object);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public static JsonData fail(String msg) {
        JsonData jsonData = new JsonData();
        jsonData.setRet(false);
        jsonData.setMsg(msg);
        return jsonData;
    }
}
