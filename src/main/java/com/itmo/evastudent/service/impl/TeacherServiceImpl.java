package com.itmo.evastudent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmo.evastudent.model.entity.Teacher;
import com.itmo.evastudent.service.TeacherService;
import com.itmo.evastudent.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

/**
* @author chenjiahan
* @description 针对表【teacher(教师表)】的数据库操作Service实现
* @createDate 2023-09-12 20:36:42
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

}




