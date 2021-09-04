package com.qrqy.mysql.entity;

import com.google.gson.JsonArray;
import com.qiniu.util.Json;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.json.JsonObject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-18 15:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_question_option_rely")
@EntityListeners(AuditingEntityListener.class)
public class FormQuestionOptionRelyEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 914733026288642966L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;

    /**
	 * 子级题目id
	 */
    @Column(name = "child_form_question_id")
    private Integer childFormQuestionId;

	/**
	 * 父级题目id
	 */
   	@Column(name = "parent_form_question_id")
    private Integer parentFormQuestionId;
	/**
	 * 选项id
	 */
   	@Column(name = "parent_form_question_option_id")
    private Integer parentFormQuestionOptionId;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
