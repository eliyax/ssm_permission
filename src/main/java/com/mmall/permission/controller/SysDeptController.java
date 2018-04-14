package com.mmall.permission.controller;


import com.mmall.permission.VO.JsonData;
import com.mmall.permission.dto.DeptLevelDto;
import com.mmall.permission.param.DeptParam;
import com.mmall.permission.service.SysDeptService;
import com.mmall.permission.service.SysTreeService;
import com.mmall.permission.utils.JsonDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {


    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysDeptService sysDeptService;

    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        log.info("Test UUID {}",UUID.randomUUID());
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonDataUtil.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id) {
        sysDeptService.delete(id);
        return JsonDataUtil.success();
    }





}
