package com.mmall.permission.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;


public class PageQuery {

    @Setter
    @Getter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Setter
    @Getter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    private int offset;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}
