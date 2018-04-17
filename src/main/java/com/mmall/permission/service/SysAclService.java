package com.mmall.permission.service;

import com.google.common.base.Preconditions;
import com.mmall.permission.beans.PageQuery;
import com.mmall.permission.beans.PageResult;
import com.mmall.permission.dao.SysAclMapper;
import com.mmall.permission.exception.ParamException;
import com.mmall.permission.model.SysAcl;
import com.mmall.permission.param.AclParam;
import com.mmall.permission.utils.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SysAclService {
    @Autowired
    private SysAclMapper sysAclMapper;

    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl acl = SysAcl.builder()
                .name(param.getName())
                .aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType())
                .status(param.getStatus())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        acl.setCode(generateCode());
        acl.setOperator("admin");
        acl.setOperateTime(new Date());
        acl.setOperateIp("127.0.0.1");
        sysAclMapper.insertSelective(acl);

    }

    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("当前权限模块下面存在相同名称的权限点");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限点不存在");

        SysAcl after = SysAcl.builder()
                .id(param.getId())
                .name(param.getName())
                .aclModuleId(param.getAclModuleId()).url(param.getUrl())
                .type(param.getType())
                .status(param.getStatus())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        after.setCode(generateCode());
        after.setOperator("admin");
        after.setOperateTime(new Date());
        after.setOperateIp("127.0.0.1");

        sysAclMapper.updateByPrimaryKeySelective(after);

    }

    public PageResult<SysAcl> getPageByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, pageQuery);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    public boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int)(Math.random() * 100);
    }



}
