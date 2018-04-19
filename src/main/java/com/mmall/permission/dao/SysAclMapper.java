package com.mmall.permission.dao;

import com.mmall.permission.beans.PageQuery;
import com.mmall.permission.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int countByAclModuleId(Integer aclModuleId);

    List<SysAcl> getPageByAclModuleId(@Param("aclModuleId")Integer aclModuleId, @Param("pageQuery") PageQuery pageQuery);

    int  countByNameAndAclModuleId(@Param("aclModuleId") int aclModuleId,@Param("name") String name, @Param("id") Integer id);

    List<SysAcl> getAll();

    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    List<SysAcl> getByUrl(String url);
}