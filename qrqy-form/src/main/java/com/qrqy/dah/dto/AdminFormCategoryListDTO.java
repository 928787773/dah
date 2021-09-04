package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-form-category-list
 * @author : QRQY
 * @date : 2021-06-29 14:23
 */
@Data
public class AdminFormCategoryListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 6038603153998952874L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;



}
