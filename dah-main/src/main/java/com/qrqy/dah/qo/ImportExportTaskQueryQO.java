package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-07-09 17:36
 */
@Data
public class ImportExportTaskQueryQO extends PageableQO {



	@ApiModelProperty(value = "文件类型", example = "")
	private ImportExportTaskFileTypeEnum fileType;


	@ApiModelProperty(value = "类型;IMPORT:导入,EXPORT:导出", example = "")
	private ImportExportTaskTypeEnum type;


}
