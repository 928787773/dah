package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * route : admin-case-info-disease-outcome-statistic
 * @author : QRQY
 * @date : 2021-06-30 17:41
 */
@Data
public class AdminCaseInfoGenderWorldMapStatisticDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2682577328457421931L;

	@ApiModelProperty(value = "经度", example = "")
	private String lon;


	@ApiModelProperty(value = "纬度", example = "")
	private String lat;

	@ApiModelProperty(value = "国家名", example = "")
	private String name;

	@ApiModelProperty(value = "性别男的个数", example = "")
	private String countGender1;

	@ApiModelProperty(value = "性别女的个数", example = "")
	private String countGender2;








}
