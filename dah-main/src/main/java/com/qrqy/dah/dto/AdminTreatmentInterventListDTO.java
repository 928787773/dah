package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-treatment-intervent-list
 * @author : QRQY
 * @date : 2021-06-25 15:00
 */
@Data
public class AdminTreatmentInterventListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 1442535901150346734L;

	private Integer id;


	private String name;


}
