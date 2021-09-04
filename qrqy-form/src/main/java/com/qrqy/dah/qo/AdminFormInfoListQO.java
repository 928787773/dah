package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 14:22
 */
@Data
public class AdminFormInfoListQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "量表分类id", example = "")
	@NotNull
	private Integer formCategoryId;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "描述", example = "")
	private String detail;


	@ApiModelProperty(value = "英文描述", example = "")
	private String enDetail;


	@ApiModelProperty(value = "权重", example = "")
	private Integer weight;


	@ApiModelProperty(value = "限时,单位分钟", example = "")
	private Integer validTime;


	@ApiModelProperty(value = "状态", example = "")
	private String status;


}
