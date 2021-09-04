package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum CaseInfoVisitTypeEnum implements BaseEnum {


    /**
     *
     */
    FIRST("初诊"),
    RETURN("复诊");


    private String message;

    CaseInfoVisitTypeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static CaseInfoVisitTypeEnum parse(String name) {

        return Arrays.stream(CaseInfoVisitTypeEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}