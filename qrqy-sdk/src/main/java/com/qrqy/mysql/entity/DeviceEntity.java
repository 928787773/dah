package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;
import java.math.BigDecimal;

/**
 * @author : Luis
 * @date : 2021-06-29 14:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
public class DeviceEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 5763541191092393505L;

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
	 * 类型
	 */
   	@Column(name = "type")
    private String type;

	/**
	 * 型号
	 */
   	@Column(name = "device_no")
    private String deviceNo;
	/**
	 * 数量
	 */
   	@Column(name = "amount")
    private Integer amount;
	/**
	 * 单价
	 */
   	@Column(name = "unit_price")
    private BigDecimal unitPrice;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
