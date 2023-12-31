package com.itmo.evastudent.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 课程表
 * @TableName course
 */
@TableName(value ="course")
@Data
public class Course implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 课程中文名称
     */
    private String courseChineseName;

    /**
     * 课程英文名
     */
    private String courseEnglishName;

    /**
     * 教师id列表【json】
     */
    private String teacherIds;

    /**
     * 学生年级
     */
    private Integer grade;

    /**
     * 专业（0-计算机，1-自动化）
     */
    private Integer major;

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