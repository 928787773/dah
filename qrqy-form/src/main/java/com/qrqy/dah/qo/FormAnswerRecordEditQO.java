package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-22 15:45
 */
@Data
public class FormAnswerRecordEditQO extends PageableQO {


	@ApiModelProperty(value = "问题id", example = "1")
	@NotNull
	private Integer formQuestionId;

	@ApiModelProperty(value = "选项id;多选用逗号拼接", example = "1")
	private String formOptionIds;


	@ApiModelProperty(value = "文字题的问题内容", example = "文字")
	private String value;

}
