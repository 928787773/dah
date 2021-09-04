package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : common-upload-file
 * @author : QRQY
 * @date : 2021-07-07 11:30
 */
@Data
public class CommonUploadFileDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2104288736111707432L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "手机号2", example = "")
	private String phonenum;

	@ApiModelProperty(value = "职位", example = "")
	private String position;

	@ApiModelProperty(value = "状态", example = "")
	private String status;

	@ApiModelProperty(value = "密码", example = "")
	private String password;

}
