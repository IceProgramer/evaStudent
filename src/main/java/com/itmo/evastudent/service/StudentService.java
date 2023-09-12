package com.itmo.evastudent.service;

import com.itmo.evastudent.model.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.evastudent.model.vo.LoginStudentVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author chenjiahan
* @description 针对表【student(学生表)】的数据库操作Service
* @createDate 2023-09-12 15:53:18
*/
public interface StudentService extends IService<Student> {

    /**
     * 用户登录
     *
     * @param studentAccount  用户账户
     * @param studentPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginStudentVO studentLogin(String studentAccount, String studentPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    Student getLoginStudent(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    boolean studentLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return
     */
    LoginStudentVO getLoginStudentVO(Student student);
}
