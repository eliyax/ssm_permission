package com.mmall.permission.VO;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TestVO {

    @NotBlank
    private String name;

    @NotNull(message = "code 不能为空")
    @Max(value = 100,message = "code 不能大于100")
    @Min(value = 1,message = "code 不能小于1")
    private Integer code;
}
