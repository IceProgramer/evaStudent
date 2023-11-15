package com.itmo.evastudent.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 教师表
 * @TableName teacher
 */
@TableName(value ="teacher")
@Data
public class Teacher implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 教师名称
     */
    private String teacherName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职位
     */
    private Integer position;

    /**
     * 职称
     */
    private Integer title;

    /**
     * 专业（0-计算机，1-自动化）
     */
    private Integer major;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 国籍（0-俄罗斯，1-中国）
     */
    private Integer nationality;

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