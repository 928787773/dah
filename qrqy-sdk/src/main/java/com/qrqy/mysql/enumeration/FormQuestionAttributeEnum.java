package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum FormQuestionAttributeEnum implements BaseEnum {


    NO("无属性"),
    TEXT("长文本"),
    DATE("日期"),
    PHONE("手机号"),
    EMAIL("邮箱");


    private String message;

    FormQuestionAttributeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static FormQuestionAttributeEnum parse(String name) {

        return Arrays.stream(FormQuestionAttributeEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}