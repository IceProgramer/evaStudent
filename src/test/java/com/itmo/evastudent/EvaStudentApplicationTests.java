package com.itmo.evastudent;
import java.util.Date;

import com.itmo.evastudent.mapper.TeacherMapper;
import com.itmo.evastudent.model.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class EvaStudentApplicationTests {

    @Resource
    TeacherMapper teacherMapper;

    @Test
    void contextLoads() {
        Teacher teacher = new Teacher();
        teacher.setTeacherName("a");
        teacher.setGender(0);
        teacher.setAge(0);
        teacher.setPosition(0);
        teacher.setTitle(0);
        teacher.setMajor(0);
        teacher.setEmail("341312314");
        teacher.setNationality(0);


        teacherMapper.insert(teacher);
    }





}
