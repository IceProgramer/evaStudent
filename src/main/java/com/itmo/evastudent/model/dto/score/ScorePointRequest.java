package com.itmo.evastudent.model.dto.score;

import lombok.Data;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/12 13:00
 */
@Data
public class ScorePointRequest {

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 一级指标id
     */
    private Long targetId;

    /**
     * 一级指标分数
     */
    private Double score;
}
