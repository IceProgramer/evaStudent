package com.itmo.evastudent.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/11 17:05
 */
@Data
public class TeacherVO {

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 一级指标列表
     */
    private List<FirstTargetVO> firstTargetVOList;
}
