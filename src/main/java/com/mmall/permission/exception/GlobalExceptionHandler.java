package com.mmall.permission.exception;


import com.mmall.permission.VO.JsonData;
import com.mmall.permission.utils.JsonDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonData handlePermissionException(PermissionException ex, HttpServletRequest request) {
        log.error("handlePermissionException,url: "+ request.getRequestURI(),ex);
        return JsonDataUtil.fail("权限校验异常");
    }

    @ExceptionHandler(ParamException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonData handleParamException(ParamException ex, HttpServletRequest request) {
        log.error("handleParamException,url: "+ request.getRequestURI(),ex);
        return JsonDataUtil.fail("参数错误");
    }
}
