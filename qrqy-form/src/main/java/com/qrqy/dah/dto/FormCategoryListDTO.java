package com.qrqy.dah.dto;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.dto.IBaseDTO;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * route : admin-form-category-query
 * @author : QRQY
 * @date : 2021-06-16 17:23
 */
@Data
public class FormCategoryListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2969941156932256232L;

	private Integer id;


	private String name;


	private FormCategoryTypeEnum type;


	private String status;

	private Long formInfoNumber;

	private Long formRecordnumber;

	private BigDecimal satisfactionNum;
}
