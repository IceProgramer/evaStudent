package com.itmo.evastudent.model.vo;

import lombok.Data;

/**
 * 课程 Request
 *
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/12 19:21
 */
@Data
public class CourseVO {
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
}
