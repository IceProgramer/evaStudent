package com.itmo.evastudent.service;

import com.itmo.evastudent.model.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmo.evastudent.model.vo.CourseInfoVO;
import com.itmo.evastudent.model.vo.CourseVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenjiahan
 * @description 针对表【course(课程表)】的数据库操作Service
 * @createDate 2023-09-12 19:29:44
 */
public interface CourseService extends IService<Course> {

    /**
     * 获取完成课程列表
     *
     * @return 课程列表
     */
    List<CourseVO> listDoneCourse(HttpServletRequest request);

    /**
     * 插入全部课程一级指标
     *
     * @param userId  用户id
     * @param request 网络请求
     */
    void insertCourseTarget(Long userId, HttpServletRequest request);

    /**
     * 获取未完成课程列表
     *
     * @param request 网络请求
     * @return 未完成课程列表
     */
    List<CourseVO> listUndoneCourse(HttpServletRequest request);

    /**
     * 根据id获取课程信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseInfoVO getCourseById(Long courseId, HttpServletRequest request);
}
