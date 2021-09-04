package com.qrqy.mysql.entity;

import com.qrqy.dp.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.BaseEntity;

/**
 * @author : Luis
 * @date : 2021-06-16 16:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_modular")
@EntityListeners(AuditingEntityListener.class)
public class SystemModularEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 3275909986768133691L;

	/**
	 * 编号
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
	 * 父级id
	 */
    @Column(name = "f_id")
    private Integer fId;
	/**
	 * 等级;0:一级;1:二级;2:三级模块;以此类推
	 */
   	@Column(name = "level")
    private Integer level;
	/**
	 * 标识
	 */
   	@Column(name = "sign")
    private String sign;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
