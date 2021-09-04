package com.qrqy.mysql.entity;

import com.qrqy.mysql.enumeration.FormQuestionAttributeEnum;
import com.qrqy.mysql.enumeration.FormQuestionOptionSortWayEnum;
import com.qrqy.mysql.enumeration.FormQuestionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;

import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-17 16:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_question")
@EntityListeners(AuditingEntityListener.class)
public class FormQuestionEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 5577394702351818469L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 编码
	 */
   	@Column(name = "code")
    private String code;
	/**
	 * 量表id
	 */
    @Column(name = "form_info_id")
    private Integer formInfoId;
	/**
	 * 题目内容
	 */
   	@Column(name = "content")
    private String content;
	/**
	 * 是否必答
	 */
   	@Column(name = "is_forced")
    private Integer isForced;
	/**
	 * 图片
	 */
   	@Column(name = "imgs")
    private String imgs;
	/**
	 * 类型 SINGLECHOICE：单选 MULTIPLECHOICE：多选 FILLIN:填空 SCORE:打分
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "type")
    private FormQuestionTypeEnum type;

	/**
	 * 等级;0是最高级别
	 */
   	@Column(name = "level")
    private Integer level;


	/**
	 * (类型是填空使用的字段)属性 NO:无属性 TEXT:长文本 DATE: 日期 PHONE:手机号 EMAIL:邮箱
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "attribute")
    private FormQuestionAttributeEnum attribute;
	/**
	 * 选项排列方式; ROW1:竖向排列 ROW2:两列 ROW3:三列 ROW4:四列 ROW5:五列 ROW6:六列 ROW7:七列 ROW8:八列 ROW9:九列 ROW10:十列
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "option_sort_way")
    private FormQuestionOptionSortWayEnum optionSortWay;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

//	/**
//	 * 一对多关联，可用于订单表中带出多条商品明显
//	 * 参照：https://my.oschina.net/liangbo/blog/92301
//	 */
//	@OneToMany(cascade = {CascadeType.ALL})
//	@JoinColumn(name = "form_question_id")
//	private List<FormQuestionOptionEntity> formQuestionOptionList;
}
