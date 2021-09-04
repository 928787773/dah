package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.BaseEntity;
import java.time.*;

/**
 * @author : Luis
 * @date : 2021-07-26 13:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "OP_CORE_DEPARTMENT")
@EntityListeners(AuditingEntityListener.class)
public class OpCoreDepartmentEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 6624512991104792219L;

	/**
	 * 主键ID
	 */
   	@Column(name = "UUID")
    private String uuid;
	/**
	 * 部门机构名称
	 */
   	@Column(name = "NAME")
    private String name;
	/**
	 * 上级单位-外键链接OP_CORE_DEPARTMENT表的部门id
	 */
   	@Column(name = "PARENT")
    private String parent;
	/**
	 * 排序
	 */
   	@Column(name = "ORDER_NUMBER")
    private Integer orderNumber;
	/**
	 * 层级，黄委0，委属1，一次类推
	 */
   	@Column(name = "LEVEL_NUMBER")
    private Integer levelNumber;
	/**
	 * 层级
	 */
   	@Column(name = "LAYER")
    private String layer;
	/**
	 * 单位简称
	 */
   	@Column(name = "SHORT_NAME")
    private String shortName;
	/**
	 * 统一支撑平台的组织机构uuid
	 */
   	@Column(name = "ZHBG_UUID")
    private String zhbgUuid;
	/**
	 * 组织部门编号
	 */
   	@Column(name = "UNIT_CODE")
    private String unitCode;
	/**
	 * 组织类型，1=单位=组织；2=部门
	 */
   	@Column(name = "DEPT_TYPE")
    private Integer deptType;
	/**
	 * 创建时间
	 */
   	@Column(name = "CREATE_TIME")
    private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
   	@Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;
	/**
	 * 创建人
	 */
   	@Column(name = "CREATE_USER")
    private String createUser;
	/**
	 * 更新人
	 */
   	@Column(name = "UPDATE_USER")
    private String updateUser;
	/**
	 * 是否有效，0=无效 1=有效
	 */
   	@Column(name = "IS_VALID")
    private Integer isValid;
	/**
	 * 删除标志，0=未删除 1=删除
	 */
   	@Column(name = "DEL_FLAG")
    private Integer delFlag;
	/**
	 * 备注
	 */
   	@Column(name = "REMARK")
    private String remark;
	/**
	 * 套表组id
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   	@Column(name = "GROUP_ID")
    private String groupId;

}
