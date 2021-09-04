package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum ImportExportTaskFileTypeEnum implements BaseEnum {


    /**
     *
     */
    caseInfo("病例"),
    material("物资"),
    drug("药品"),
    device("设备"),
    formCategory("调查问卷"),
    medicalStaff("医务人员");


    private String message;

    ImportExportTaskFileTypeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ImportExportTaskFileTypeEnum parse(String name) {

        return Arrays.stream(ImportExportTaskFileTypeEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}