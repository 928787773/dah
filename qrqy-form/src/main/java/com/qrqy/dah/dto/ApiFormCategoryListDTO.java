package com.qrqy.dah.dto;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import com.qrqy.dp.dto.IBaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * route : admin-form-category-query
 * @author : QRQY
 * @date : 2021-06-16 17:23
 */
@Data
public class ApiFormCategoryListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2969941156932256232L;

	private Integer id;


	private String name;


	private String note;

}
