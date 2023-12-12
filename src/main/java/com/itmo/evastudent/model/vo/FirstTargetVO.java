package com.itmo.evastudent.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 评价指标表
 */
@Data
public class FirstTargetVO implements Serializable {
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
     * 指标状态 0 - 未完成 1 - 已完成
     */
    private Integer status;
}