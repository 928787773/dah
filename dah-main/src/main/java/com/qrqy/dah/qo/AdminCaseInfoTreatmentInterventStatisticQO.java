package com.qrqy.dah.qo;

import com.qrqy.dah.utils.CommonUtils;
import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-07-01 13:51
 */
@Data
public class AdminCaseInfoTreatmentInterventStatisticQO extends PageableQO {

	@ApiModelProperty(value = "国家id", example = "1")
	private Integer countryId;

	@ApiModelProperty(value = "性别", example = "1")
	private String gender;

	@ApiModelProperty(value = "年龄开始", example = "1")
	private Long ageGte;

	@ApiModelProperty(value = "年龄结束", example = "1")
	private Long ageLte;

	@ApiModelProperty(value = "添加时间开始时间", example = "12")
	private LocalDate createdAtGte;

	@ApiModelProperty(value = "添加时间结束时间", example = "12")
	private LocalDate createdAtLte;


}
