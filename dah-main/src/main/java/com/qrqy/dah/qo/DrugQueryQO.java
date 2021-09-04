package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-29 15:18
 */
@Data
public class DrugQueryQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "添加结束时间", example = "2020-01-11")
	private LocalDateTime createdAtLte;

	@ApiModelProperty(value = "添加开始时间", example = "2020-01-11")
	private LocalDateTime createdAtGte;

	@ApiModelProperty(value = "名称", example = "")
	private String nameLike;

}
