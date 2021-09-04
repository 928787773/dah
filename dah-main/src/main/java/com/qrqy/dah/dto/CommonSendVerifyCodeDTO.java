package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : common-send-verify-code
 * @author : QRQY
 * @date : 2021-06-15 09:55
 */
@Data
public class CommonSendVerifyCodeDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 8407027448647368395L;

	private Integer id;

	private String phonenum;


	private String code;


	private Integer status;


}
