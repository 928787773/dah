package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum FormQuestionTypeEnEnum implements BaseEnum {


    /**
     *
     */
    SINGLECHOICE("Single choice"),
    MULTIPLECHOICE("Multiple choice"),
    FILLIN("Fill in the blanks"),
    SCORE("Scoring");


    private String message;

    FormQuestionTypeEnEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static FormQuestionTypeEnEnum parse(String name) {

        return Arrays.stream(FormQuestionTypeEnEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}