package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * route : admin-export-excel
 * @author : QRQY
 * @date : 2021-07-01 16:04
 */
@Data
public class AdminExportExcelDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 1916081328087650601L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "量表分类id", example = "")
	private Integer formCategoryId;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "描述", example = "")
	private String detail;

	@ApiModelProperty(value = "英文描述", example = "")
	private String enDetail;

	@ApiModelProperty(value = "权重", example = "")
	private Integer weight;

	@ApiModelProperty(value = "限时,单位分钟", example = "")
	private Integer validTime;

	@ApiModelProperty(value = "状态", example = "")
	private String status;

}
