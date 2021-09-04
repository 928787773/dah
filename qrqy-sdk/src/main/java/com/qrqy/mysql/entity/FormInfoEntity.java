package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-17 16:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_info")
@EntityListeners(AuditingEntityListener.class)
public class FormInfoEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 4626589541723322980L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 量表分类id
	 */
    @Column(name = "form_category_id")
    private Integer formCategoryId;
	/**
	 * 名称
	 */
   	@Column(name = "name")
    private String name;
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
	 * 权重
	 */
   	@Column(name = "weight")
    private Integer weight;
	/**
	 * 限时,单位分钟
	 */
   	@Column(name = "valid_time")
    private Integer validTime;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
