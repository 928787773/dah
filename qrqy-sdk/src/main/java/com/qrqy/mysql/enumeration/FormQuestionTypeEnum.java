package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum FormQuestionTypeEnum implements BaseEnum {


    SINGLECHOICE("单选"),
    MULTIPLECHOICE("多选"),
    FILLIN("填空"),
    SCORE("打分");


    private String message;

    FormQuestionTypeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static FormQuestionTypeEnum parse(String name) {

        return Arrays.stream(FormQuestionTypeEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}