package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-08-23 11:51
 */
@Data
public class ApiFormInfoListQO extends PageableQO {



	@ApiModelProperty(value = "量表分类id", example = "")
	@NotNull
	private Integer formCategoryId;


}
