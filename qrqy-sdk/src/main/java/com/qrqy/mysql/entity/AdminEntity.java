package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-10 16:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin")
@EntityListeners(AuditingEntityListener.class)
public class AdminEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 2796086590345361606L;

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
	 * 手机号2
	 */
   	@Column(name = "phonenum")
    private String phonenum;
	/**
	 * 职位
	 */
	@Column(name = "position")
	private String position;

	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;





}
