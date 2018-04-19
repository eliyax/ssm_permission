package com.mmall.permission.service;

import com.google.common.collect.Lists;
import com.mmall.permission.dao.SysAclMapper;
import com.mmall.permission.dao.SysRoleAclMapper;
import com.mmall.permission.dao.SysRoleUserMapper;
import com.mmall.permission.model.SysAcl;
import com.mmall.permission.model.SysUser;
import com.mmall.permission.utils.HostHolder;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysCoreService {
    @Autowired
    private SysAclMapper sysAclMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;
//    @Autowired
//    private SysCacheService sysCacheService;

    public List<SysAcl> getCurrentUserAclList() {
        int userId = HostHolder.getUser().getId();
        return getUserAclList(userId);
    }

    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList((ArrayList<Integer>) userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = HostHolder.getUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

//    public boolean hasUrlAcl(String url) {
//        if (isSuperAdmin()) {
//            return true;
//        }
//        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
//        if (CollectionUtils.isEmpty(aclList)) {
//            return true;
//        }
//
//        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
//        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());
//
//        boolean hasValidAcl = false;
//        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
//        for (SysAcl acl : aclList) {
//            // 判断一个用户是否具有某个权限点的访问权限
//            if (acl == null || acl.getStatus() != 1) { // 权限点无效
//                continue;
//            }
//            hasValidAcl = true;
//            if (userAclIdSet.contains(acl.getId())) {
//                return true;
//            }
//        }
//        if (!hasValidAcl) {
//            return true;
//        }
//        return false;
//    }
//
//    public List<SysAcl> getCurrentUserAclListFromCache() {
//        int userId = HostHolder.getUser().getId();
//        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
//        if (StringUtils.isBlank(cacheValue)) {
//            List<SysAcl> aclList = getCurrentUserAclList();
//            if (CollectionUtils.isNotEmpty(aclList)) {
//                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
//            }
//            return aclList;
//        }
//        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
//        });
//    }
}
