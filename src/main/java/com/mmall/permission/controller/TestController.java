package com.mmall.permission.controller;

import com.mmall.permission.VO.TestVO;
import com.mmall.permission.exception.ParamException;
import com.mmall.permission.exception.PermissionException;
import com.mmall.permission.utils.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        log.info("hello");
        throw new PermissionException();
//        return "hello permission";
    }

    @GetMapping("/valid")
    public String valid(TestVO vo) throws ParamException {
        log.info("valid");
        BeanValidator.check(vo);
        return "通过验证";
    }
}
