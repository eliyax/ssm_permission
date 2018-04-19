package com.mmall.permission.controller;

import com.mmall.permission.VO.JsonData;
import com.mmall.permission.param.AclModuleParam;
import com.mmall.permission.service.SysAclModuleService;
import com.mmall.permission.service.SysTreeService;
import com.mmall.permission.utils.JsonDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {
    @Autowired
    private SysAclModuleService sysAclModuleService;

    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        return JsonDataUtil.success(sysTreeService.aclModuleTree());
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id) {
        sysAclModuleService.delete(id);
        return JsonDataUtil.success();
    }

}
