package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-disease-type-list
 * @author : QRQY
 * @date : 2021-06-25 14:47
 */
@Data
public class AdminDiseaseTypeListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 3264824877633875758L;

	private Integer id;


	private String name;


}
