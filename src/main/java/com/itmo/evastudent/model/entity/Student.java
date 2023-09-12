package com.itmo.evastudent.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学生表
 * @TableName student
 */
@TableName(value ="student")
@Data
public class Student implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生密码
     */
    private String studentPassword;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生性别（0 - 女 1 - 男）
     */
    private Integer gender;

    /**
     * 学生年龄
     */
    private Integer age;

    /**
     * 学生班级id
     */
    private Long studentClassId;

    /**
     * 专业（0 - 计算机 1 - 自动化）
     */
    private Integer major;

    /**
     * 学生年级
     */
    private Integer grade;

    /**
     * 学生状态（0 - 正常使用 1 - 拒绝访问）
     */
    private Integer studentStatus;

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
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}