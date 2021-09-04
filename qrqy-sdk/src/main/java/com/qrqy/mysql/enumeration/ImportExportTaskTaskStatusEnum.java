package com.qrqy.mysql.enumeration;

import com.qrqy.dp.enumeration.BaseEnum;

import java.util.Arrays;

/**
 * @author : Luis
 * @date : 2021/6/17 10:21
 */
public enum ImportExportTaskTaskStatusEnum implements BaseEnum {


    /**
     *
     */
    noStart("未开始"),
    check("数据检验中"),
    start("进行中"),
    finish("已完成"),
    dataError("数据存在错误"),
    fail("失败");


    private String message;

    ImportExportTaskTaskStatusEnum(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ImportExportTaskTaskStatusEnum parse(String name) {

        return Arrays.stream(ImportExportTaskTaskStatusEnum.values()).filter(t -> t.name().equals(name)).findAny().orElse(null);
    }
}