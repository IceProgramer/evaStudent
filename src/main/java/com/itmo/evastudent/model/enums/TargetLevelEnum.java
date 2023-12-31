package com.itmo.evastudent.model.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/IceProgramer">chenjiahan</a>
 * @create 2023/9/6 16:24
 */
public enum TargetLevelEnum {

    FIRST_LEVEL("一级指标", 0),
    SECOND_LEVEL("二级指标", 1);

    private final Integer value;

    private final String name;

    TargetLevelEnum(String name, Integer value) {
        this.value = value;
        this.name = name;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.name).collect(Collectors.toList());
    }

    public static List<Integer> getIntegerValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static TargetLevelEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (TargetLevelEnum anEnum : TargetLevelEnum.values()) {
            if (anEnum.name.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 根据 name 获取枚举
     *
     * @param value
     * @return
     */
    public static TargetLevelEnum getEnumByName(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (TargetLevelEnum anEnum : TargetLevelEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
