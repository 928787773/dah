package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * route : admin-form-info-query
 * @author : QRQY
 * @date : 2021-06-21 10:34
 */
@Data
public class ApiFormInfoListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 57994997280481281L;

	private Integer id;


	private String name;


	private String note;






}
