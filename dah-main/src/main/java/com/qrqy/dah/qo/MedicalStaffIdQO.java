package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 13:45
 */
@Data
public class MedicalStaffIdQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	@NotNull
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "性别;0:保密;1:男;2:女", example = "")
	private Integer gender;


	@ApiModelProperty(value = "医院", example = "")
	private String hospital;


	@ApiModelProperty(value = "科室", example = "")
	private String department;


	@ApiModelProperty(value = "职位", example = "")
	private String position;


	@ApiModelProperty(value = "职务", example = "")
	private String positionContent;


	@ApiModelProperty(value = "政治面貌", example = "")
	private String politicCountenance;


	@ApiModelProperty(value = "手机号", example = "")
	private String phonenum;


	@ApiModelProperty(value = "外语等级", example = "")
	private String foreignLanguageLevel;


	@ApiModelProperty(value = "状态", example = "")
	private String status;


}
