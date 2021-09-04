package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.entity.FormLevelRuleEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-17 16:34
 */
@Data
public class FormInfoEditQO extends PageableQO {

	private Integer id;

	@ApiModelProperty(value = "问卷方向id", example = "1")
	@NotNull
	private Integer formCategoryId;

	@ApiModelProperty(value = "问卷名称", example = "患者满意度")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "描述", example = "这是一个患者满意度的问卷")
	@NotBlank
	private String detail;


	@ApiModelProperty(value = "英文描述", example = "This is a questionnaire of patients' satisfaction")
	private String enDetail;


	@ApiModelProperty(value = "权重", example = "2")
	@NotNull
	private Integer weight;

	@ApiModelProperty(value = "时长", example = "30")
	@NotNull
	private Integer validTime;

	@ApiModelProperty(value = "英文名称", example = "Patient satisfaction")
	private String note;

	@ApiModelProperty(value = "量表评定规则", example = "")
	@NotNull
	private FormLevelRuleQO formLevelRule;



}
