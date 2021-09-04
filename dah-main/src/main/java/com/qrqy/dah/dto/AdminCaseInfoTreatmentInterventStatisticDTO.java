package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-case-info-treatment-intervent-statistic
 * @author : QRQY
 * @date : 2021-07-01 13:51
 */
@Data
public class AdminCaseInfoTreatmentInterventStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 6728905058704485112L;

	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "值", example = "")
	private String value;


}
