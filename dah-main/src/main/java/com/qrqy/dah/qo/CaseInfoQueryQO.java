package com.qrqy.dah.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 *
 * @author : QRQY
 * @date : 2021-06-25 14:09
 */
@Data
public class CaseInfoQueryQO extends PageableQO {

    @ApiModelProperty(value = "国家id", example = "1")
    private Integer countryId;


    @ApiModelProperty(value = "姓名", example = "张三")
    private String nameLike;


    @ApiModelProperty(value = "添加结束时间", example = "2020-01-11")
    private LocalDateTime createdAtLte;

    @ApiModelProperty(value = "添加开始时间", example = "2020-01-11")
    private LocalDateTime createdAtGte;


    @ApiModelProperty(value = "性别", example = "1")
    private String gender;


    @ApiModelProperty(value = "结束年龄", example = "12")
    private Integer ageLte;

    @ApiModelProperty(value = "开始年龄", example = "12")
    private Integer ageGte;



    @ApiModelProperty(value = "人员性质id", example = "1")
    private Integer userTypeId;


    @ApiModelProperty(value = "疾病范畴id", example = "1")
    private Integer diseaseTypeId;


    private String diseaseContent;


    @ApiModelProperty(value = "疾病转归id", example = "1")
    private Integer diseaseOutcomeId;


    @ApiModelProperty(value = "疾病严重程度", example = "1")
    private String diseaseSeverity;


    @ApiModelProperty(value = "是否中医干预", example = "1")
    private Integer isTcmIntervention;

    private Integer pastPhysicalConditionId;


    @ApiModelProperty(value = "就诊情况", example = "FIRST")
    private CaseInfoVisitTypeEnum visitType;


    @ApiModelProperty(value = "治疗干预方式id", example = "1")
    private Integer treatmentInterventId;


}
