package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-16 10:36
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "verify_code")
@EntityListeners(AuditingEntityListener.class)
public class VerifyCodeEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 3130362789094028048L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 手机号
	 */
   	@Column(name = "phonenum")
    private String phonenum;
	/**
	 * 验证码
	 */
   	@Column(name = "code")
    private String code;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
