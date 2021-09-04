package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * route : admin-form-info-get
 * @author : QRQY
 * @date : 2021-06-24 09:37
 */
@Data
public class ApiFormInfoDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 5837306395471032773L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "问卷名称", example = "")
	private String name;

	@ApiModelProperty(value = "英文名称", example = "")
	private String note;

	@ApiModelProperty(value = "描述", example = "")
	private String detail;

	@ApiModelProperty(value = "英文描述", example = "")
	private String enDetail;

	@ApiModelProperty(value = "限时", example = "")
	private Integer validTime;


}
