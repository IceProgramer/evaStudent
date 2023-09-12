package com.itmo.evastudent.controller;

import com.itmo.evastudent.common.BaseResponse;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.common.ResultUtils;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.model.dto.student.StudentLoginRequest;
import com.itmo.evastudent.model.entity.Student;
import com.itmo.evastudent.model.vo.LoginStudentVO;
import com.itmo.evastudent.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 学生相关
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 16:01
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Resource
    private StudentService studentService;

    /**
     * 学生登录
     *
     * @param studentLoginRequest 学生登录
     * @param request 网络请求
     * @return 学生信息
     */
    @PostMapping("/login")
    public BaseResponse<LoginStudentVO> studentLogin(@RequestBody StudentLoginRequest studentLoginRequest, HttpServletRequest request) {
        if (studentLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String studentAccount = studentLoginRequest.getUserAccount();
        String studentPassword = studentLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(studentAccount, studentPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginStudentVO loginStudentVO = studentService.studentLogin(studentAccount, studentPassword, request);
        return ResultUtils.success(loginStudentVO);
    }


    /**
     * 学生注销
     *
     * @param request 请求
     * @return 是否注销
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> studentLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = studentService.studentLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录学生
     *
     * @param request 请求
     * @return 登录信息
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginStudentVO> getLoginStudent(HttpServletRequest request) {
        Student student = studentService.getLoginStudent(request);
        return ResultUtils.success(studentService.getLoginStudentVO(student));
    }
}
