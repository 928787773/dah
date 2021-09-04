package com.qrqy.dah.qo;

import com.qrqy.dp.qo.PageableQO;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.*;

/**
 * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn
 * @author : QRQY
 * @date : 2021-08-09 09:49
 */
@Data
public class ImportExportTaskIdQO extends PageableQO {

	@ApiModelProperty(value = "id", example = "")
	@NotNull
	private Integer id;


}
