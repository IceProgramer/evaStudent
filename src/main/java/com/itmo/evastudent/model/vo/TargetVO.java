package com.itmo.evastudent.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 评价指标表
 */
@Data
public class TargetVO implements Serializable {
    /**
     * id
     */
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
}