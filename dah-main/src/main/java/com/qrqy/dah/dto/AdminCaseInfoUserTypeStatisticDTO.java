package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-case-info-user-type-statistic
 * @author : QRQY
 * @date : 2021-07-01 14:26
 */
@Data
public class AdminCaseInfoUserTypeStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3754641652772022963L;

	@ApiModelProperty(value = "y轴数值", example = "")
	private List<String> yAxisData;


	@ApiModelProperty(value = "值", example = "")
	private List<String> seriesData;



}
