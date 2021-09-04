package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * route : admin-device-get
 * @author : QRQY
 * @date : 2021-06-29 15:23
 */
@Data
public class DeviceDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3536992952858017967L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "型号", example = "")
	private String deviceNo;

	@ApiModelProperty(value = "类型", example = "")
	private String type;

	@ApiModelProperty(value = "数量", example = "")
	private Integer amount;


	@ApiModelProperty(value = "单价", example = "")
	private BigDecimal unitPrice;


	@ApiModelProperty(value = "总金额", example = "")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "录入时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@ApiModelProperty(value = "备注", example = "")
	private String note;




}
