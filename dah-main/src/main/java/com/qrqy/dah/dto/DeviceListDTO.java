package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * route : admin-device-query
 * @author : QRQY
 * @date : 2021-06-29 15:24
 */
@Data
public class DeviceListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 4515524368797950620L;

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


}
