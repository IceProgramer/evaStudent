package com.itmo.evastudent.service;

import com.itmo.evastudent.model.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
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
     * @param request
     */
    void insertCourseTarget(Long userId, HttpServletRequest request);
}
