package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-25 14:20
 */
@Data
public class AdminCountryListQO extends PageableQO {

	private Integer id;

	private String shortName;

	private String name;

	private String enName;

	private String lon;

	private String lat;

	private String code;

	private Integer status;

	private Integer sort;

}
