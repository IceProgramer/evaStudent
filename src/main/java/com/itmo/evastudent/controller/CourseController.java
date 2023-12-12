package com.itmo.evastudent.controller;

import com.itmo.evastudent.common.BaseResponse;
import com.itmo.evastudent.common.ErrorCode;
import com.itmo.evastudent.common.ResultUtils;
import com.itmo.evastudent.exception.BusinessException;
import com.itmo.evastudent.model.vo.CourseInfoVO;
import com.itmo.evastudent.model.vo.CourseVO;
import com.itmo.evastudent.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 19:32
 */
@RestController
@RequestMapping("/student/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 获取完成课程信息
     *
     * @return 完成课程列表
     */
    @GetMapping("/get/done/list")
    public BaseResponse<List<CourseVO>> listDoneCourse(HttpServletRequest request) {

        List<CourseVO> courseVOList = courseService.listDoneCourse(request);

        return ResultUtils.success(courseVOList);
    }

    /**
     * 获取未完成课程信息
     *
     * @return 未完成课程列表
     */
    @GetMapping("/get/undone/list")
    public BaseResponse<List<CourseVO>> listUndoneCourse(HttpServletRequest request) {

        List<CourseVO> courseVOList = courseService.listUndoneCourse(request);

        return ResultUtils.success(courseVOList);
    }

    /**
     * 根据id获取课程
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    @GetMapping("/get/course/info")
    public BaseResponse<CourseInfoVO> getCourseVOById(Long courseId) {
        if (courseId == null || courseId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "课程id为空");
        }

        CourseInfoVO courseVO = courseService.getCourseById(courseId);

        return ResultUtils.success(courseVO);
    }
}
