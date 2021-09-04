package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-11 09:33
 */
@Data
public class AdminQueryQO extends PageableQO {


	@ApiModelProperty(value = "姓名", example = "管理")
	private String nameLike;

	@ApiModelProperty(value = "手机号", example = "15662545784")
	private String phonenumLike;





}
