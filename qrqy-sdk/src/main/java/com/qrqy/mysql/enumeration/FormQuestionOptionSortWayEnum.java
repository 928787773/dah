package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum FormQuestionOptionSortWayEnum implements BaseEnum {


    /**
     *
     */
    ROW1("竖向排列"),
    ROW2("两列"),
    ROW3("三列"),
    ROW4("四列"),
    ROW5("五列"),
    ROW6("六列"),
    ROW7("七列"),
    ROW8("八列"),
    ROW9("九列"),
    ROW10("十列");


    private String message;

    FormQuestionOptionSortWayEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static FormQuestionOptionSortWayEnum parse(String name) {

        return Arrays.stream(FormQuestionOptionSortWayEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}