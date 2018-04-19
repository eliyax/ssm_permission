package com.mmall.permission.service;

import com.google.common.base.Preconditions;
import com.mmall.permission.param.DeptParam;
import com.mmall.permission.dao.SysDeptMapper;
import com.mmall.permission.exception.ParamException;
import com.mmall.permission.model.SysDept;
import com.mmall.permission.utils.BeanValidator;
import com.mmall.permission.utils.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;

    public void save(DeptParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept dept = SysDept.builder()
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        dept.setOperateIp("127.0.0.1");//TODO:
        dept.setOperator("admin");//TODO:
        dept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(dept);//只更新非null的
    }


    public void update(DeptParam param) {
        BeanValidator.check(param);
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        if (checkExist(before.getParentId(), before.getName(), before.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept after = SysDept.builder()
                .id(before.getId())
                .name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq())
                .remark(param.getRemark())
                .build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperateIp("127.0.0.1");//TODO:
        after.setOperator("admin-update");//TODO:
        after.setOperateTime(new Date());

        updateWithChild(before, after);

    }

    @Transactional
    void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            //找到以当前id的所有子部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    public void delete(int deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            return null;
        }
        return dept.getLevel();
    }
}
