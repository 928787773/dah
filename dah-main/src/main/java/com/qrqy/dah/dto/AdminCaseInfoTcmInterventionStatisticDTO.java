package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-case-info-tcm-intervention-statistic
 * @author : QRQY
 * @date : 2021-07-01 14:04
 */
@Data
public class AdminCaseInfoTcmInterventionStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 370755902646757352L;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "值", example = "")
	private String value;


}
