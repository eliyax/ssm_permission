package com.mmall.permission.VO;

import lombok.Data;

@Data
public class JsonData<T>{

    private boolean ret;

    private String msg;

    private T data;

}
