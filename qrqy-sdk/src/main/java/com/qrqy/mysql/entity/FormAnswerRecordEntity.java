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
 * @date : 2021-06-22 15:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_answer_record")
@EntityListeners(AuditingEntityListener.class)
public class FormAnswerRecordEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 8870354487457273152L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 答题记录id
	 */
    @Column(name = "form_record_id")
    private Integer formRecordId;
	/**
	 * 题目id
	 */
   	@Column(name = "form_question_id")
    private Integer formQuestionId;
	/**
	 * 选项id
	 */
   	@Column(name = "form_option_ids")
    private String formOptionIds;
	/**
	 * 得分
	 */
   	@Column(name = "score")
    private BigDecimal score;
	/**
	 * 输入内容
	 */
   	@Column(name = "value")
    private String value;
	/**
	 * 当前答题记录json
	 */
   	@Column(name = "current_form_answer_record_json")
    private String currentFormAnswerRecordJson;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
