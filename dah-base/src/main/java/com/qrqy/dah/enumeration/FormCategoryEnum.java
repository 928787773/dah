package com.qrqy.dah.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum FormCategoryEnum implements BaseEnum {

    /**
     *
     * INTERNAL：内部的
     * EXTERNAL：外部的
     *
     */
    INTERNAL("内部"),
    EXTERNAL("外部");


    private String message;

    FormCategoryEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static FormCategoryEnum parse(String name) {

        return Arrays.stream(FormCategoryEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}