package com.qrqy.dah.qo;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-07-14 13:56
 */
@Data
public class MedicalStaffQueryQO extends PageableQO {


	@ApiModelProperty(value = "添加结束时间", example = "2020-01-11")
	private LocalDateTime createdAtLte;

	@ApiModelProperty(value = "添加开始时间", example = "2020-01-11")
	private LocalDateTime createdAtGte;

	@ApiModelProperty(value = "名称", example = "")
	private String nameLike;


	@ApiModelProperty(value = "性别;0:保密;1:男;2:女", example = "")
	private Integer gender;
}
