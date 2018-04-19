package com.mmall.permission.controller;

import com.google.common.collect.Maps;
import com.mmall.permission.VO.JsonData;
import com.mmall.permission.beans.PageQuery;
import com.mmall.permission.model.SysRole;
import com.mmall.permission.param.AclParam;
import com.mmall.permission.service.SysAclService;
import com.mmall.permission.service.SysRoleService;
import com.mmall.permission.utils.JsonDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {
    @Autowired
    private SysAclService sysAclService;

    @Autowired
    private SysRoleService sysRoleService;


    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonDataUtil.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    @RequestMapping("acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", roleList);
        map.put("users", sysRoleService.getUserListByRoleList(roleList));
        return JsonDataUtil.success(map);
    }
}
