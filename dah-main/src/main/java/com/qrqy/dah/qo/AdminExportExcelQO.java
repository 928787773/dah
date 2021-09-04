package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-07-01 16:04
 */
@Data
public class AdminExportExcelQO extends PageableQO {

	@ApiModelProperty(value = "量表分类id", example = "")
	@NotNull
	private Integer formCategoryId;

//	@ApiModelProperty(value = "类型", example = "formCategory")
//	@NotBlank
//	private String fileType;

	@ApiModelProperty(value = "开始时间", example = "2021-05-01")
	private String startDate;

	@ApiModelProperty(value = "结束时间", example = "2021-06-01")
	private String endDate;





}
