package com.qrqy.dah.dto;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * route : admin-form-category-get
 * @author : QRQY
 * @date : 2021-06-17 15:52
 */
@Data
public class FormCategoryDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 7604259990607075626L;

	@ApiModelProperty(value = "ID", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "领域", example = "")
	private FormCategoryTypeEnum type;

	@ApiModelProperty(value = "英文名称", example = "")
	private String note;


}
