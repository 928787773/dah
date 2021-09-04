package com.qrqy.dah.qo;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-17 15:33
 */
@Data
public class FormCategoryEditQO extends PageableQO {

	@ApiModelProperty(value = "ID", example = "11")
	private Integer id;

	@NotBlank
	@ApiModelProperty(value = "问卷方向名称", example = "方向名称")
	private String name;


	@NotNull
	@ApiModelProperty(value = "问卷方向领域", example = "INTERNAL")
	private FormCategoryTypeEnum type;

	@NotBlank
	@ApiModelProperty(value = "英文名称", example = "NAME")
	private String note;


}
