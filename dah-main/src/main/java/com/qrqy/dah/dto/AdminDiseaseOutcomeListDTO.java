package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-disease-outcome-list
 * @author : QRQY
 * @date : 2021-06-25 14:48
 */
@Data
public class AdminDiseaseOutcomeListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 367398832712618822L;

	private Integer id;


	private String name;


}
