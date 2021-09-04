package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.FormQuestionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * route : admin-form-info-get
 * @author : QRQY
 * @date : 2021-06-24 09:37
 */
@Data
public class FormInfoDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 5837306395471032773L;

	private Integer id;


	private Integer formCategoryId;


	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "英文名称", example = "")
	private String note;

	@ApiModelProperty(value = "描述", example = "")
	private String detail;

	@ApiModelProperty(value = "英文描述", example = "")
	private String enDetail;


	@ApiModelProperty(value = "权重", example = "")
	private Integer weight;


	@ApiModelProperty(value = "时长", example = "")
	private Integer validTime;


	@ApiModelProperty(value = "满意分数", example = "")
	private BigDecimal formLevelRuleScore;

	@ApiModelProperty(value = "最大分数", example = "")
	private BigDecimal maxTotalSore;



}
