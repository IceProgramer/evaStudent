package com.itmo.evastudent.model.dto.student;

import lombok.Data;

/**
 * 学生登录 Request
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 16:07
 */
@Data
public class StudentLoginRequest {

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
