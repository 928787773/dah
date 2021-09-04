package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-system-modular-list
 * @author : QRQY
 * @date : 2021-06-16 16:41
 */
@Data
public class AdminSystemModularListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2778868557757649181L;

	private Integer id;


	private String name;


	private Integer level;


	private String sign;


	private Integer status;


}
