package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-case-info-disease-severity-statistic
 * @author : QRQY
 * @date : 2021-07-01 14:09
 */
@Data
public class AdminCaseInfoDiseaseSeverityStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 4232718230069171995L;

	@ApiModelProperty(value = "X轴数据", example = "")
	private String[] xAxisData;


	@ApiModelProperty(value = "值", example = "")
	private List<String> seriesData;




}
