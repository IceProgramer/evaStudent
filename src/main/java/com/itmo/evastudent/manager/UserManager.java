package com.itmo.evastudent.manager;

import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.mapper.StudentMapper;
import com.itmo.evastudent.model.entity.Student;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.itmo.evastudent.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/11/13 21:07
 */
@Component
public class UserManager {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 获取当前登录学生信息
     *
     * @param request 网络请求
     * @return 登录学生信息
     */
    public Student getLoginStudent(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        Student currentUser = (Student) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }

        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = studentMapper.selectById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

}
