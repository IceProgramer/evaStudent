package com.itmo.evastudent.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 评价指标表
 * @TableName target
 */
@TableName(value ="target")
@Data
public class Target implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 指标中文名称
     */
    private String targetChineseName;

    /**
     * 指标英文名称
     */
    private String targetEnglishName;

    /**
     * 指标等级
     */
    private Integer targetLevel;

    /**
     * 国籍（0-俄罗斯，1-中国）
     */
    private Integer nationality;

    /**
     * 指标id
     */
    private Long targetId;

    /**
     * 指标权重
     */
    private BigDecimal weight;

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