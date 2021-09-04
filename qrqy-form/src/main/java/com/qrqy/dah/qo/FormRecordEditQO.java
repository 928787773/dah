package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-06-22 15:36
 */
@Data
public class FormRecordEditQO extends PageableQO {

	@ApiModelProperty(value = "问卷id", example = "1")
	@NotNull
	private Integer formInfoId;

	@ApiModelProperty(value = "耗时", example = "10:20")
	private String useTime;

//	@ApiModelProperty(value = "账户类型;ADMIN:管理后台;DEVICE:设备", example = "ADMIN")
//	private String accountType;
//
	@ApiModelProperty(value = "设备号", example = "11222")
	private String deviceNo;

	@ApiModelProperty(value = "答案数组", example = "")
	private List<FormAnswerRecordEditQO> formAnswerRecordList;


}
