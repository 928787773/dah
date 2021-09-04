package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-case-info-age-statistic
 * @author : QRQY
 * @date : 2021-06-30 16:17
 */
@Data
public class AdminCaseInfoAgeStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 6756554142919453421L;

	@ApiModelProperty(value = "X轴数据", example = "")
	private String[] xAxisData;


	@ApiModelProperty(value = "值", example = "")
	private List<String> seriesData;


}
