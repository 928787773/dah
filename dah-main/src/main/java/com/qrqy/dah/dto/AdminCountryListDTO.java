package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-country-list
 * @author : QRQY
 * @date : 2021-06-25 14:20
 */
@Data
public class AdminCountryListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 5403962002034223516L;

	private Integer id;


	private String shortName;


	private String name;


	private String enName;


	private String lon;


	private String lat;


	private String code;



}
