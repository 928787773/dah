package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-user-type-list
 * @author : QRQY
 * @date : 2021-06-25 14:28
 */
@Data
public class AdminUserTypeListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 7389659736858374641L;

	private Integer id;


	private String name;


}
