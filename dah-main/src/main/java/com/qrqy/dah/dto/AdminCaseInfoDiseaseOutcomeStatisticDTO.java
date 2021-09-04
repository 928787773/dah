package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-case-info-disease-outcome-statistic
 * @author : QRQY
 * @date : 2021-06-30 17:41
 */
@Data
public class AdminCaseInfoDiseaseOutcomeStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2682577328457421931L;

	@ApiModelProperty(value = "X轴数据", example = "")
	private List<String> yAxisData;


	@ApiModelProperty(value = "值", example = "")
	private List<String> seriesData;


}
