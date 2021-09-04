package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * route : admin-form-info-query
 * @author : QRQY
 * @date : 2021-06-21 10:34
 */
@Data
public class FormInfoListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 57994997280481281L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "权重", example = "")
	private Integer weight;

	@ApiModelProperty(value = "填报时长", example = "")
	private Integer validTime;

	@ApiModelProperty(value = "状态", example = "")
	private String status;

	@ApiModelProperty(value = "填报人数", example = "")
	private Long formRecordnumber;

	@ApiModelProperty(value = "满意度", example = "")
	private BigDecimal satisfactionNum;




}
