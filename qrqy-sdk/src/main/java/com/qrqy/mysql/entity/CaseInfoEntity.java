package com.qrqy.mysql.entity;

import com.qrqy.mysql.enumeration.CaseInfoVisitTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import com.qrqy.dp.entity.CommonEntity;

/**
 *
 * 其中的多对一关联其他表如果关联的数据没有查到会报错,多对一不要关联可以动态修改的表
 * @author : Luis
 * @date : 2021-06-25 14:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "case_info")
@EntityListeners(AuditingEntityListener.class)
public class CaseInfoEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 6194585275691784522L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 国家id
	 */
    @Column(name = "country_id")
    private Integer countryId;

	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="country_id", insertable = false, updatable = false)
	private CountryEntity country;
	/**
	 * 姓名
	 */
   	@Column(name = "name")
    private String name;
	/**
	 * 性别;0:保密;1:男;2:女
	 */
   	@Column(name = "gender")
    private String gender;
	/**
	 * 生日
	 */
   	@Column(name = "birthday")
    private LocalDate birthday;
	/**
	 * 用户类型id
	 */
   	@Column(name = "user_type_id")
    private Integer userTypeId;
	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="user_type_id", insertable = false, updatable = false)
	private UserTypeEntity userType;
	/**
	 * 疾病类型id
	 */
   	@Column(name = "disease_type_id")
    private Integer diseaseTypeId;
	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="disease_type_id", insertable = false, updatable = false)
	private DiseaseTypeEntity diseaseType;
	/**
	 * 疾病诊断内容
	 */
   	@Column(name = "disease_content")
    private String diseaseContent;
	/**
	 * 疾病转归id
	 */
   	@Column(name = "disease_outcome_id")
    private Integer diseaseOutcomeId;
	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="disease_outcome_id", insertable = false, updatable = false)
	private DiseaseOutcomeEntity diseaseOutcome;
	/**
	 * 参加的项目
	 */
   	@Column(name = "join_project")
    private String joinProject;
	/**
	 * 疾病严重程度
	 */
   	@Column(name = "disease_severity")
    private String diseaseSeverity;
	/**
	 * 是否中医干预;0:否;1:是
	 */
   	@Column(name = "is_tcm_intervention")
    private Integer isTcmIntervention;
	/**
	 * 检查结果
	 */
   	@Column(name = "inspection_result")
    private String inspectionResult;
	/**
	 * 既往身体情况id
	 */
   	@Column(name = "past_physical_condition_id")
    private Integer pastPhysicalConditionId;
	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="past_physical_condition_id", insertable = false, updatable = false)
	private PastPhysicalConditionEntity pastPhysicalCondition;
	/**
	 * 既往情况内容
	 */
   	@Column(name = "past_physical_condition_content")
    private String pastPhysicalConditionContent;
	/**
	 * 药物使用情况
	 */
   	@Column(name = "drug_use_info")
    private String drugUseInfo;
	/**
	 * 就诊类型;FIRST:初诊;RETURN:复诊
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "visit_type")
    private CaseInfoVisitTypeEnum visitType;
	/**
	 * 病史
	 */
   	@Column(name = "disease_history")
    private String diseaseHistory;
	/**
	 * 治疗干预方式id
	 */
   	@Column(name = "treatment_intervent_id")
    private Integer treatmentInterventId;
	/**
	 * 多对一关联，可用于订单表中带出用户信息
	 * 参照：https://my.oschina.net/liangbo/blog/92301
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name="treatment_intervent_id", insertable = false, updatable = false)
	private TreatmentInterventEntity treatmentIntervent;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
