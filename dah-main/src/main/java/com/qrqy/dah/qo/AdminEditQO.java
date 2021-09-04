package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-10 15:46
 */
@Data
public class AdminEditQO extends PageableQO {

	@ApiModelProperty(value = "ID", example = "11")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "张三")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "手机号", example = "13111111111")
	@NotBlank
	private String phonenum;

	@ApiModelProperty(value = "职称", example = "工作人员")
	@NotBlank
	private String position;

	@ApiModelProperty(value = "密码,md5格式", example = "e10adc3949ba59abbe56e057f20f883e")
	@NotBlank
	private String password;

	@ApiModelProperty(value = "权限", example = "1,2,3")
	@NotBlank
	private String systemModularIds;






}
