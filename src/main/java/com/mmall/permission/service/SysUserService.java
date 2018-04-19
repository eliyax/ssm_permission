package com.mmall.permission.service;

import com.google.common.base.Preconditions;
import com.mmall.permission.beans.PageQuery;
import com.mmall.permission.beans.PageResult;
import com.mmall.permission.dao.SysUserMapper;
import com.mmall.permission.exception.ParamException;
import com.mmall.permission.model.SysUser;
import com.mmall.permission.param.UserParam;
import com.mmall.permission.utils.BeanValidator;
import com.mmall.permission.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysUserService {
    private static final String PASSWORD_PREFIX = "PERMISSION_";

    @Autowired
    private SysUserMapper sysUserMapper;

    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话号码已注册");
        }
        if (checkMailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已注册");
        }
        //TODO:
        String password = "12345678";
        String encryptPassword = MD5Util.encrypt(password);
        SysUser user = SysUser.builder().build();
        BeanUtils.copyProperties(param, user);
        user.setPassword(encryptPassword);
        user.setOperator("admin");
        user.setOperateIp("127.0.0.1");
        user.setOperateTime(new Date());

        sysUserMapper.insertSelective(user);
    }

    public void update(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkMailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().build();
        BeanUtils.copyProperties(param, after);
        after.setOperator("admin");
        after.setOperateIp("127.0.0.1");
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0) {
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    private boolean checkMailExist(String mail, Integer id) {
        return sysUserMapper.countByMail(mail, id) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer id) {
        return sysUserMapper.countByTelephone(telephone, id) > 0;
    }

    public SysUser findByKeyword(String username) {
        return sysUserMapper.findByKeyword(username);
    }

    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}
