package com.mmall.permission.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonMapper {

    private  static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // config
        // 忽略json字符串中不识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    public static <T> String obj2String(T src) {
        if (src == null) {
            return null;
        }
        try {
            return src instanceof String ? (String) src : objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            log.warn("parse object to String exception, error:{}", e);
            return null;
        }
    }

    public static <T> T string2Obj(String src, TypeReference<T> typeReference) {
        if (src == null || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
        } catch (Exception e) {
            log.warn("parse String to Object exception, String:{}, TypeReference<T>:{}, error:{}", src, typeReference.getType(), e);
            return null;
        }
    }

}
