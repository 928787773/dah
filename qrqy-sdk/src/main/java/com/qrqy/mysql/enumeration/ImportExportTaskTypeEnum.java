package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum ImportExportTaskTypeEnum implements BaseEnum {


    /**
     *
     */
    IMPORT("导入"),
    EXPORT("导出");


    private String message;

    ImportExportTaskTypeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ImportExportTaskTypeEnum parse(String name) {

        return Arrays.stream(ImportExportTaskTypeEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}