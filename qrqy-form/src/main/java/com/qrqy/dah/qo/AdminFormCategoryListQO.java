package com.qrqy.dah.qo;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 14:23
 */
@Data
public class AdminFormCategoryListQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "类型;INTERNAL:内部EXTERNAL:外部", example = "")
	private FormCategoryTypeEnum type;


	@ApiModelProperty(value = "状态", example = "")
	private String status;


}
