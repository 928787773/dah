package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;


/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-21 10:06
 */
@Data
public class FormQuestionOptionEditQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "code唯一标识", example = "")
	@NotBlank
	private String code;

	@ApiModelProperty(value = "选项内容", example = "")
	private String content;



	@ApiModelProperty(value = "选项描述", example = "")
	private String detail;

	@ApiModelProperty(value = "选项英文描述", example = "")
	private String enDetail;

	@ApiModelProperty(value = "分数", example = "")
	private BigDecimal score;

	@ApiModelProperty(value = "图片", example = "")
	private String imgs;


	@ApiModelProperty(value = "选项英文内容", example = "")
	private String note;

	@ApiModelProperty(value = "删除", example = "")
	private Integer deletedFlag;




}
