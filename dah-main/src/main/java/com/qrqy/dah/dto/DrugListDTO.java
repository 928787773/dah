package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * route : admin-drug-query
 * @author : QRQY
 * @date : 2021-06-29 15:18
 */
@Data
public class DrugListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 4189010616379910224L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "数量", example = "")
	private Integer amount;

	@ApiModelProperty(value = "规格", example = "")
	private String specs;


	@ApiModelProperty(value = "单位", example = "")
	private String unit;


	@ApiModelProperty(value = "成分", example = "")
	private String component;


	@ApiModelProperty(value = "单价", example = "")
	private BigDecimal unitPrice;


	@ApiModelProperty(value = "录入时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;


}
