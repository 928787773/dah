package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 15:17
 */
@Data
public class DrugEditQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "数量", example = "")
	private Integer amount;

	@ApiModelProperty(value = "规格", example = "")
	private String specs;


	@ApiModelProperty(value = "单位", example = "")
	private String unit;


	@ApiModelProperty(value = "成分", example = "")
	private String component;


	@ApiModelProperty(value = "单价", example = "")
	private BigDecimal unitPrice;


}
