package com.mmall.permission.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.permission.dao.SysLogMapper;
import com.mmall.permission.dao.SysRoleUserMapper;
import com.mmall.permission.dao.SysUserMapper;
import com.mmall.permission.model.SysRoleUser;
import com.mmall.permission.model.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleUserService {


    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysLogMapper sysLogMapper;

    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return;
            }
        }
        updateRoleUsers(roleId, userIdList);
//        saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    @Transactional
    private void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(userIdList)) {
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .operator("admin")
                    .operateIp("127.0.0.1")
                    .operateTime(new Date())
                    .build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }
}
