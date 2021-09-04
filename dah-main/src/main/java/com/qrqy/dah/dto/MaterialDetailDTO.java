package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * route : admin-material-get
 * @author : QRQY
 * @date : 2021-06-29 14:42
 */
@Data
public class MaterialDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 851949996993355695L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "规格", example = "")
	private String specs;


	@ApiModelProperty(value = "数量", example = "")
	private Integer amount;


	@ApiModelProperty(value = "单位", example = "")
	private BigDecimal unitPrice;


	@ApiModelProperty(value = "总金额", example = "")
	private BigDecimal totalPrice;

	@ApiModelProperty(value = "备注", example = "")
	private String note;

	@ApiModelProperty(value = "录入时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;




}
