package com.itmo.evastudent.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 一级指标分数表
 * @TableName targetScore
 */
@TableName(value ="targetScore")
@Data
public class TargetScore implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 一级指标id
     */
    private Long targetId;

    /**
     * 评测id
     */
    private Long evaluationId;

    /**
     * 一级分数
     */
    private BigDecimal score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}