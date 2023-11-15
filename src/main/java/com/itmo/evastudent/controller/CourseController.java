package com.itmo.evastudent.controller;

import com.itmo.evastudent.common.BaseResponse;
import com.itmo.evastudent.common.ResultUtils;
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
}
