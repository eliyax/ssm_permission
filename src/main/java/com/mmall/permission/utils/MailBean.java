package com.mmall.permission.utils;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailBean {
    //设置邮件的主题
    private String subject;

    //邮件正文消息
    private String message;

    //接收者邮箱
    private Set<String> receivers;
}
