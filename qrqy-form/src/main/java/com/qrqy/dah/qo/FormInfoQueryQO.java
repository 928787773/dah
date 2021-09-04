package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-21 10:34
 */
@Data
public class FormInfoQueryQO extends PageableQO {


	@ApiModelProperty(value = "问卷方向id", example = "1")
	private Integer formCategoryId;

	@ApiModelProperty(value = "名称", example = "问卷名称")
	private String nameLike;

	@ApiModelProperty(value = "状态", example = "1")
	private String status;


}
