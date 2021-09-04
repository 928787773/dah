package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.*;
import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.*;

/**
 * route : admin-case-info-query
 * @author : QRQY
 * @date : 2021-06-25 14:09
 */
@Data
public class CaseListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 5063171651838673703L;

	private Integer id;


	@ApiModelProperty(value = "国家", example = "")
	private CountryEntity country;

	@ApiModelProperty(value = "姓名", example = "")
	private String name;

	@ApiModelProperty(value = "性别", example = "")
	private String gender;

	@ApiModelProperty(value = "性别文字", example = "")
	private String genderStr;


	private LocalDate birthday;

	@ApiModelProperty(value = "年龄", example = "")
	private Integer age;

	@ApiModelProperty(value = "人员性质", example = "")
	private UserTypeEntity userType;

	@ApiModelProperty(value = "疾病范畴", example = "")
	private DiseaseTypeEntity diseaseType;


	private String diseaseContent;

	@ApiModelProperty(value = "疾病转归", example = "")
	private DiseaseOutcomeEntity diseaseOutcome;


	private String joinProject;


	@ApiModelProperty(value = "疾病严重程度", example = "")
	private String diseaseSeverity;

	@ApiModelProperty(value = "是否疾病干预", example = "")
	private Integer isTcmIntervention;


	private String inspectionResult;

	@ApiModelProperty(value = "既往身体情况", example = "")
	private PastPhysicalConditionEntity pastPhysicalCondition;


	private String pastPhysicalConditionContent;


	private String drugUseInfo;


	@ApiModelProperty(value = "就诊情况", example = "")
	private CaseInfoVisitTypeEnum visitType;


	private String diseaseHistory;


	@ApiModelProperty(value = "治疗方式", example = "")
	private TreatmentInterventEntity treatmentIntervent;


	private String status;

	@ApiModelProperty(value = "添加时间", example = "")
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;


}
