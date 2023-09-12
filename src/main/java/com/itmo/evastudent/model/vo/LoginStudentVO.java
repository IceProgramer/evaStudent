package com.itmo.evastudent.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 16:05
 */
@Data
public class LoginStudentVO {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
