package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-case-info-gender-statistic
 * @author : QRQY
 * @date : 2021-06-29 17:34
 */
@Data
public class AdminCaseInfoGenderStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 5361965278376968222L;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "值", example = "")
	private String value;


}
