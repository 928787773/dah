package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-form-info-list
 * @author : QRQY
 * @date : 2021-06-29 14:22
 */
@Data
public class AdminFormInfoListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2938980136004975331L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;

}
