package com.mmall.permission.dao;

import com.mmall.permission.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface SysRoleAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleAcl record);

    int insertSelective(SysRoleAcl record);

    SysRoleAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleAcl record);

    int updateByPrimaryKey(SysRoleAcl record);

    List<Integer> getRoleIdListByAclId(int aclId);

    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") ArrayList<Integer> roleIdList);

    void deleteByRoleId(int roleId);

    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);
}