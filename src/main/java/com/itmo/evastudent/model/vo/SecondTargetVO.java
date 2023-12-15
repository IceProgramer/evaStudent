package com.itmo.evastudent.model.vo;

import lombok.Data;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/12/15 14:15
 */
@Data
public class SecondTargetVO {

    /**
     * id
     */
    private Long id;

    /**
     * 二级指标中文名称
     */
    private String targetChineseName;

    /**
     * 二级指标英文名称
     */
    private String targetEnglishName;
}
