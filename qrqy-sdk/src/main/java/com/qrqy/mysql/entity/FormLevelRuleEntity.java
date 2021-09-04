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
 * @date : 2021-06-17 16:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_level_rule")
@EntityListeners(AuditingEntityListener.class)
public class FormLevelRuleEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 3102594245723766110L;

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
	 * 等级:DISSATISFACTION:不满意;PARTLYDISSATISFACTION:比较不满意;PARTLYSATISFACTION: 比较满意;SATISFACTION:满意;HIGHLYSATISFACTORY:非常满意
	 */
   	@Column(name = "level")
    private String level;
	/**
	 * 开始分数
	 */
   	@Column(name = "start_score")
    private BigDecimal startScore;
	/**
	 * 结束分数
	 */
   	@Column(name = "end_score")
    private BigDecimal endScore;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
