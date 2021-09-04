package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * route : admin-import-export-task-query
 * @author : QRQY
 * @date : 2021-07-09 17:36
 */
@Data
public class ImportExportTaskListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 4016167272012016177L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;


	@ApiModelProperty(value = "文件路径", example = "")
	private String filePath;


	@ApiModelProperty(value = "导入或导出状态;noStart:未开始check:数据检验中start:开始finish:完成fail:失败dataError:数据错误", example = "")
	private ImportExportTaskTaskStatusEnum taskStatus;


	@ApiModelProperty(value = "文件类型", example = "")
	private ImportExportTaskFileTypeEnum fileType;


	@ApiModelProperty(value = "类型;IMPORT:导入,EXPORT:导出", example = "")
	private ImportExportTaskTypeEnum type;


	@ApiModelProperty(value = "创建时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;



	@ApiModelProperty(value = "完成时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime finishAt;




}
