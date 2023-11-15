package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.constant.EvaluationContent;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.manager.UserManager;
import com.itmo.evastudent.mapper.EvaluationMapper;
import com.itmo.evastudent.model.entity.Evaluation;
import com.itmo.evastudent.model.entity.Student;
import com.itmo.evastudent.model.enums.EvaluationStatusEnum;
import com.itmo.evastudent.model.vo.LoginStudentVO;
import com.itmo.evastudent.service.CourseService;
import com.itmo.evastudent.service.StudentService;
import com.itmo.evastudent.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.itmo.evastudent.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author chenjiahan
 * @description 针对表【student(学生表)】的数据库操作Service实现
 * @createDate 2023-09-12 15:53:18
 */
@Service
@Slf4j
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Resource
    private CourseService courseService;

    @Resource
    private EvaluationMapper evaluationMapper;

    @Resource
    private UserManager userManager;


    @Override
    public LoginStudentVO studentLogin(String studentAccount, String studentPassword, HttpServletRequest request) {
        Evaluation evaluation = evaluationMapper.selectOne(Wrappers.<Evaluation>lambdaQuery()
                .eq(Evaluation::getEvaluationStatus, EvaluationStatusEnum.OPENING.getValue())
                .select(Evaluation::getId)
                .last("limit 1"));
        if (evaluation == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "没有评测正在进行中");
        }
        EvaluationContent.set(evaluation);

        // 1. 校验
        if (StringUtils.isAnyBlank(studentAccount, studentPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (studentAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号错误");
        }
        if (studentPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex(studentPassword.getBytes());
        // 查询用户是否存在
        Student student = baseMapper.selectOne(new LambdaQueryWrapper<Student>()
                .eq(Student::getStudentNumber, studentAccount)
                .eq(Student::getStudentPassword, encryptPassword)
                .last("limit 1"));
        // 用户不存在
        if (student == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, student);
        LoginStudentVO loginStudentVO = this.getLoginStudentVO(student);

        // 判断是否插入课程一级指标
        courseService.insertCourseTarget(student.getId(), request);
        return loginStudentVO;
    }

    @Override
    public Student getLoginStudent(HttpServletRequest request) {
        return userManager.getLoginStudent(request);
    }

    @Override
    public boolean studentLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginStudentVO getLoginStudentVO(Student student) {
        if (student == null) {
            return null;
        }

        LoginStudentVO loginUserVO = new LoginStudentVO();

        BeanUtils.copyProperties(student, loginUserVO);
        loginUserVO.setUsername(student.getStudentName());

        return loginUserVO;
    }
}




