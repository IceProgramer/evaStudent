package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.Course;
import com.itmo.evastudent.service.CourseService;
import com.itmo.evastudent.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【course(课程表)】的数据库操作Service实现
* @createDate 2023-09-12 15:53:18
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService {

}




