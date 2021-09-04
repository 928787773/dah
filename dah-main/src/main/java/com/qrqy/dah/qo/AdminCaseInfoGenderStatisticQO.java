package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 17:34
 */
@Data
public class AdminCaseInfoGenderStatisticQO extends PageableQO {

    @ApiModelProperty(value = "国家id", example = "1")
    private Integer countryId;

    @ApiModelProperty(value = "添加时间", example = "2021-02-01")
    private LocalDate createdAtGte;

    @ApiModelProperty(value = "添加时间", example = "2021-02-05")
    private LocalDate createdAtLte;

    @ApiModelProperty(value = "年龄开始", example = "1")
    private Long ageGte;

    @ApiModelProperty(value = "年龄结束", example = "1")
    private Long ageLte;



}
