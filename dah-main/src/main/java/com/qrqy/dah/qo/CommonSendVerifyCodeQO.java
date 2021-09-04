package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-15 09:55
 */
@Data
public class CommonSendVerifyCodeQO extends PageableQO {


	@ApiModelProperty(value = "手机号", example = "13111111111")
	@NotBlank
	private String phonenum;


}
