package com.mmall.permission.dao;

import com.mmall.permission.beans.PageQuery;
import com.mmall.permission.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByDeptId(int deptId);

    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("pageQuery") PageQuery pageQuery);

    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone")String telephone, @Param("id")Integer id);

    SysUser findByKeyword(String username);

    List<SysUser> getByIdList(@Param("userIdList") List<Integer> userIdList);

    List<SysUser> getAll();
}