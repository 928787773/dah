package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * route : admin-medical-staff-get
 * @author : QRQY
 * @date : 2021-06-29 13:45
 */
@Data
public class MedicalStaffDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 49160854989005137L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "名称", example = "")
	private String name;


	@ApiModelProperty(value = "性别;0:保密;1:男;2:女", example = "")
	private Integer gender;

	@ApiModelProperty(value = "性别;0:保密;1:男;2:女", example = "")
	private String genderStr;

	@ApiModelProperty(value = "医院", example = "")
	private String hospital;


	@ApiModelProperty(value = "科室", example = "")
	private String department;


	@ApiModelProperty(value = "职位", example = "")
	private String position;


	@ApiModelProperty(value = "职务", example = "")
	private String positionContent;


	@ApiModelProperty(value = "政治面貌", example = "")
	private String politicCountenance;


	@ApiModelProperty(value = "手机号", example = "")
	private String phonenum;


	@ApiModelProperty(value = "外语等级", example = "")
	private String foreignLanguageLevel;



	@ApiModelProperty(value = "备注", example = "")
	private String note;

	@ApiModelProperty(value = "录入时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

}
