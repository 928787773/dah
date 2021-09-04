package com.qrqy.mysql.entity;

import com.qrqy.dp.annocation.BucketType;
import com.qrqy.dp.annocation.QiniuKodo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author : Luis
 * @date : 2021-06-17 16:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_question_option")
@EntityListeners(AuditingEntityListener.class)
public class FormQuestionOptionEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 3675013896730163856L;

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
	 * 量表题目id
	 */
    @Column(name = "form_question_id")
    private Integer formQuestionId;
	/**
	 * 选项内容
	 */
   	@Column(name = "content")
    private String content;
	/**
	 * 描述
	 */
   	@Column(name = "detail")
    private String detail;
	/**
	 * 英文描述
	 */
   	@Column(name = "en_detail")
    private String enDetail;
	/**
	 * 分数
	 */
   	@Column(name = "score")
    private BigDecimal score;
	/**
	 * 图片
	 */
	@QiniuKodo(type = BucketType.PUBLIC)
   	@Column(name = "imgs")
    private String imgs;
	/**
	 * 选择题类型: NO-不要填写具体描述 NOTE-需要填写具体描述
	 */
   	@Column(name = "type")
    private String type;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

//   	/**
//	 * 子集
//	 */
//	/**
//	 * 一对多关联，可用于订单表中带出多条商品明显
//	 * 参照：https://my.oschina.net/liangbo/blog/92301
//	 */
//	@OneToMany(cascade = {CascadeType.ALL})
//	@JoinColumn(name = "id")
//    private List<FormQuestionEntity> itemFormQuestionList;




}
