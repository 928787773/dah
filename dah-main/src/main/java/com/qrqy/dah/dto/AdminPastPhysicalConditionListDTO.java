package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-past-physical-condition-list
 * @author : QRQY
 * @date : 2021-06-25 14:59
 */
@Data
public class AdminPastPhysicalConditionListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3717994282213039674L;

	private Integer id;


	private String name;


}
