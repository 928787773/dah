package com.qrqy.dah.qo;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-16 17:23
 */
@Data
public class FormCategoryQueryQO extends PageableQO {

	@ApiModelProperty(value = "名称", example = "综合")
	private String nameLike;


	@ApiModelProperty(value = "状态", example = "1")
	private String status;

	@ApiModelProperty(value = "领域", example = "INTERNAL")
	private FormCategoryTypeEnum type;




}
