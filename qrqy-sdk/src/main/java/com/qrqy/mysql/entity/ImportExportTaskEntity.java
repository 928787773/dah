package com.qrqy.mysql.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.mysql.enumeration.ImportExportTaskFileTypeEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTaskStatusEnum;
import com.qrqy.mysql.enumeration.ImportExportTaskTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-07-01 16:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "import_export_task")
@EntityListeners(AuditingEntityListener.class)
public class ImportExportTaskEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 5885201374299762867L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 文件路径
	 */
   	@Column(name = "file_path")
    private String filePath;
	/**
	 * 导入或导出状态;
	 * noStart:未开始
	 * check:数据检验中
	 * start:开始
	 * finish:完成
	 * fail:失败
	 * dataError:数据错误
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "task_status")
    private ImportExportTaskTaskStatusEnum taskStatus;
	/**
	 * 文件类型
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "file_type")
    private ImportExportTaskFileTypeEnum fileType;
	/**
	 * 类型;import:导入,export:导出
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "type")
    private ImportExportTaskTypeEnum type;

	/**
	 * 完成时间
	 */
   	@Column(name = "finish_at")
    private LocalDateTime finishAt;

	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;




}
