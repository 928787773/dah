package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.*;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * route : admin-case-info-get
 * @author : QRQY
 * @date : 2021-06-25 14:11
 */
@Data
public class CaseDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 8270055636761120322L;

	@ApiModelProperty(value = "id", example = "")
	private Integer id;

	@ApiModelProperty(value = "国家id", example = "")
	private Integer countryId;

	@ApiModelProperty(value = "国家", example = "")
	private CountryEntity country;

	@ApiModelProperty(value = "名称", example = "")
	private String name;

	@ApiModelProperty(value = "性别", example = "")
	private String gender;

	@ApiModelProperty(value = "性别文字", example = "")
	private String genderStr;

	@ApiModelProperty(value = "年龄", example = "")
	private Integer age;

	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@ApiModelProperty(value = "人员性质id", example = "")
	private Integer userTypeId;

	@ApiModelProperty(value = "人员性质", example = "")
	private UserTypeEntity userType;

	@ApiModelProperty(value = "疾病范畴id", example = "")
	private Integer diseaseTypeId;

	@ApiModelProperty(value = "疾病范畴", example = "")
	private DiseaseTypeEntity diseaseType;



	@ApiModelProperty(value = "疾病诊断内容", example = "")
	private String diseaseContent;

	@ApiModelProperty(value = "疾病诊断内容id", example = "")
	private Integer diseaseOutcomeId;

	@ApiModelProperty(value = "疾病诊断内容", example = "")
	private DiseaseOutcomeEntity diseaseOutcome;


	@ApiModelProperty(value = "参加项目", example = "")
	private String joinProject;


	@ApiModelProperty(value = "疾病严重程度", example = "")
	private String diseaseSeverity;

	@ApiModelProperty(value = "是否中医干预", example = "")
	private Integer isTcmIntervention;

	@ApiModelProperty(value = "检查结果", example = "")
	private String inspectionResult;

	@ApiModelProperty(value = "既往身体情况id", example = "")
	private Integer pastPhysicalConditionId;

	@ApiModelProperty(value = "既往身体情况", example = "")
	private PastPhysicalConditionEntity pastPhysicalCondition;

	@ApiModelProperty(value = "既往情况内容", example = "")
	private String pastPhysicalConditionContent;

	@ApiModelProperty(value = "药物使用方式", example = "")
	private String drugUseInfo;

	@ApiModelProperty(value = "就诊类型", example = "")
	private CaseInfoVisitTypeEnum visitType;

	@ApiModelProperty(value = "病史", example = "")
	private String diseaseHistory;

	@ApiModelProperty(value = "治疗干预方式id", example = "")
	private Integer treatmentInterventId;

	@ApiModelProperty(value = "治疗干预方式", example = "")
	private TreatmentInterventEntity treatmentIntervent;

	@ApiModelProperty(value = "备注", example = "")
	private String note;

	@ApiModelProperty(value = "添加时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;


}
