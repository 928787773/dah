package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-21 09:48
 */
@Data
public class FormQuestionEditQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "题目内容", example = "")
	@NotBlank
	private String content;

	@ApiModelProperty(value = "量表id", example = "")
	@NotNull
	private Integer formInfoId;


	@ApiModelProperty(value = "是否必答", example = "")
	@NotNull
	private Integer isForced;


	@ApiModelProperty(value = "图片", example = "")
	private String imgs;


	@ApiModelProperty(value = "类型", example = "")
	@NotNull
	private FormQuestionTypeEnum type;

	@ApiModelProperty(value = "英文内容", example = "1")
	private String note;

	@ApiModelProperty(value = "排序", example = "1")
	private Integer sort;

	@ApiModelProperty(value = "属性", example = "")
	private FormQuestionAttributeEnum attribute;


	@ApiModelProperty(value = "排列方式", example = "")
	private FormQuestionOptionSortWayEnum optionSortWay;


	@ApiModelProperty(value = "选项列表", example = "")
	private List<FormQuestionOptionEditQO> formQuestionOptionList;
//
//	@ApiModelProperty(value = "需要删除的选项id数组", example = "")
//	private List<Integer> deleteOptionIdArray;

	@ApiModelProperty(value = "关联题目", example = "")
	private FormQuestionOptionRelyEditQO formQuestionOptionRely;






}
