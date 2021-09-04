package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.*;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-25 14:10
 */
@Data
public class CaseInfoEditQO extends PageableQO {

	private Integer id;

	@ApiModelProperty(value = "国家id", example = "1")
	@NotNull
	private Integer countryId;

	@ApiModelProperty(value = "姓名", example = "张三")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "性别", example = "1")
	@NotBlank
	private String gender;

	@ApiModelProperty(value = "出生日期", example = "1991-01-11")
	@NotNull
	private LocalDate birthday;

	@ApiModelProperty(value = "人员性质id", example = "1")
	@NotNull
	private Integer userTypeId;

	@ApiModelProperty(value = "疾病类型id", example = "1")
	@NotNull
	private Integer diseaseTypeId;

	@ApiModelProperty(value = "疾病诊断内容", example = "疾病诊断内容")
	@NotBlank
	private String diseaseContent;

	@ApiModelProperty(value = "疾病转归id", example = "1")
	@NotNull
	private Integer diseaseOutcomeId;

	@ApiModelProperty(value = "参加项目", example = "项目名")
	@NotBlank
	private String joinProject;

	@ApiModelProperty(value = "疾病严重程度", example = "1")
	@NotBlank
	private String diseaseSeverity;

	@ApiModelProperty(value = "是否中医干预;0:否;1:是", example = "1")
	@NotNull
	private Integer isTcmIntervention;

	@ApiModelProperty(value = "检查结果", example = "检查结果")
	@NotBlank
	private String inspectionResult;

	@ApiModelProperty(value = "既往身体情况id", example = "1")
	@NotNull
	private Integer pastPhysicalConditionId;

	@ApiModelProperty(value = "既往情况内容", example = "既往情况内容")
	private String pastPhysicalConditionContent;

	@ApiModelProperty(value = "药物使用情况", example = "药物使用情况")
	@NotBlank
	private String drugUseInfo;

	@ApiModelProperty(value = "就诊类型;FIRST:初诊;RETURN:复诊", example = "RETURN")
	@NotNull
	private CaseInfoVisitTypeEnum visitType;

	@ApiModelProperty(value = "病史", example = "病史")
	@NotBlank
	private String diseaseHistory;

	@ApiModelProperty(value = "治疗干预方式id", example = "1")
	@NotNull
	private Integer treatmentInterventId;

	@ApiModelProperty(value = "备注", example = "备注")
	private String note;


}
