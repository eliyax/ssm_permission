package com.mmall.permission.dao;

import com.mmall.permission.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    SysRoleUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleUser record);

    int updateByPrimaryKey(SysRoleUser record);

    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    List<Integer> getRoleIdListByUserId(int userId);

    List<Integer> getUserIdListByRoleId(int roleId);

    void deleteByRoleId(int roleId);

    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);
}