package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-17 15:29
 */
@Data
public class FormCategoryIdQO extends PageableQO {

	@ApiModelProperty(value = "问卷方向id", example = "2")
	@NotNull
	private Integer id;

}
