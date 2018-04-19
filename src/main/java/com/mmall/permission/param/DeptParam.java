package com.mmall.permission.param;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(min = 2, max = 15, message = "部门名称应在2~15个子之间")
    private String name;

    private Integer parentId = 0;//增加默认值

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;

    @Length(max = 150, message = "备注最多不超过150个字")
    private String remark;

}
