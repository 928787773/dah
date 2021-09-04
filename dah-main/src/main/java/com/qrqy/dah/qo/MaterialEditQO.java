package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 14:40
 */
@Data
public class MaterialEditQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	@NotBlank
	private String name;


	@ApiModelProperty(value = "规格", example = "")
	@NotBlank
	private String specs;


	@ApiModelProperty(value = "数量", example = "")
	@NotNull
	private Integer amount;


	@ApiModelProperty(value = "单价", example = "")
	@NotNull
	private BigDecimal unitPrice;

	@ApiModelProperty(value = "备注", example = "")
	private String note;




}
