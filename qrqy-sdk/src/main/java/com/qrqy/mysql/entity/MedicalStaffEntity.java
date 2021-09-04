package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-29 11:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "medical_staff")
@EntityListeners(AuditingEntityListener.class)
public class MedicalStaffEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 7001196619733054938L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 名称
	 */
   	@Column(name = "name")
    private String name;
	/**
	 * 性别;0:保密;1:男;2:女
	 */
   	@Column(name = "gender")
    private Integer gender;
	/**
	 * 医院
	 */
   	@Column(name = "hospital")
    private String hospital;
	/**
	 * 科室
	 */
   	@Column(name = "department")
    private String department;
	/**
	 * 职位
	 */
   	@Column(name = "position")
    private String position;
	/**
	 * 职务
	 */
   	@Column(name = "position_content")
    private String positionContent;
	/**
	 * 政治面貌
	 */
   	@Column(name = "politic_countenance")
    private String politicCountenance;
	/**
	 * 手机号
	 */
   	@Column(name = "phonenum")
    private String phonenum;
	/**
	 * 外语等级
	 */
   	@Column(name = "foreign_language_level")
    private String foreignLanguageLevel;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
