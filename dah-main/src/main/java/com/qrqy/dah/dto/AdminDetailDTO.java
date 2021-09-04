package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-get
 * @author : QRQY
 * @date : 2021-06-11 09:50
 */
@Data
public class AdminDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 1513315758540666580L;

	private Integer id;


	private String name;


	private String phonenum;


	private String position;

	private String[] systemModularIdArray;


}
