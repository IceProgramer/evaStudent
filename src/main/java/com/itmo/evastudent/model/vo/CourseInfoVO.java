package com.itmo.evastudent.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/11 08:50
 */
@Data
public class CourseInfoVO {
    /**
     * id
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseChineseName;

    /**
     * 课程英文名
     */
    private String courseEnglishName;

    /**
     * 教师信息列表
     */
    private List<TeacherVO> teacherList;
}
