package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-22 15:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_record")
@EntityListeners(AuditingEntityListener.class)
public class FormRecordEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 238974564680836203L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 量表id
	 */
    @Column(name = "form_info_id")
    private Integer formInfoId;
	/**
	 * 耗时(分钟)
	 */
   	@Column(name = "use_time")
    private String useTime;
	/**
	 * 账户类型;admin:管理后台;device:设备
	 */
   	@Column(name = "account_type")
    private String accountType;
	/**
	 * 设备号
	 */
   	@Column(name = "device_no")
    private String deviceNo;
	/**
	 * 总得分
	 */
   	@Column(name = "total_score")
    private BigDecimal totalScore;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
