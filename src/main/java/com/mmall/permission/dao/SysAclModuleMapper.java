package com.mmall.permission.dao;

import com.mmall.permission.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByParentId(Integer id);

    List<SysAclModule> getChildAclModuleListByLevel(String level);

    void batchUpdateLevel(List<SysAclModule> aclModuleList);

    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String aclModuleName, @Param("id") Integer id);

    List<SysAclModule> getAllAclModule();
}