package com.qrqy.dah.qo;

import com.qrqy.dp.qo.IBaseQO;
import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-07-01 16:04
 */
@Data
public class AdminImportExcelQO implements IBaseQO {


	@ApiModelProperty(value = "类型;caseInfo病例,material物资,drug药品,device设备,formCategory调查问卷,medicalStaff医务人员", example = "")
	@NotNull
	private ImportExportTaskFileTypeEnum fileType;

	@ApiModelProperty(value = "导入文件路径", example = "")
	@NotBlank
	private String filePath;




}
