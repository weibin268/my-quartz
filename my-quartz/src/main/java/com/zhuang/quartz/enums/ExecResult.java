package com.zhuang.quartz.enums;

import java.util.Arrays;

public enum ExecResult {
    SUCCESS(0, "成功"),
    ERROR(1, "失败");

    private Integer value;
    private String name;

    ExecResult(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static ExecResult getByValue(Integer value) {
        return Arrays.stream(ExecResult.values()).filter(c -> c.getValue().equals(value)).findFirst().orElse(null);
    }
}
