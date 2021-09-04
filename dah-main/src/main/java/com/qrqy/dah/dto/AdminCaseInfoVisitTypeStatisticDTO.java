package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-case-info-visit-type-statistic
 * @author : QRQY
 * @date : 2021-07-01 14:31
 */
@Data
public class AdminCaseInfoVisitTypeStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2968087817304596382L;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "值", example = "")
	private String value;




}
