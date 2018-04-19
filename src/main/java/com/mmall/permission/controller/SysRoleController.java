package com.mmall.permission.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mmall.permission.VO.JsonData;
import com.mmall.permission.model.SysUser;
import com.mmall.permission.param.RoleParam;
import com.mmall.permission.service.*;
import com.mmall.permission.utils.JsonDataUtil;
import com.mmall.permission.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysTreeService sysTreeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Autowired
    private SysRoleAclService sysRoleAclService;


    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonDataUtil.success();
    }

    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonDataUtil.success(sysRoleService.getAll());
    }

    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonDataUtil.success(sysTreeService.roleTree(roleId));
    }

    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonDataUtil.success();
    }

    @RequestMapping("/changeUsers.json")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return JsonDataUtil.success();
    }

    @RequestMapping("/users.json")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unselectedUserList = Lists.newArrayList();

        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
        for(SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
                unselectedUserList.add(sysUser);
            }
        }
        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unselectedUserList);
        return JsonDataUtil.success(map);
    }


}
