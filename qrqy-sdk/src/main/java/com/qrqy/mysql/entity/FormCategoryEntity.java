package com.qrqy.mysql.entity;

import com.qrqy.dah.enumeration.FormCategoryTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-16 17:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_category")
@EntityListeners(AuditingEntityListener.class)
public class FormCategoryEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 8258474611806565047L;

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
	 * 类型;INTERNAL:内部 EXTERNAL:外部
	 */
	@Enumerated(EnumType.STRING)
   	@Column(name = "type")
    private FormCategoryTypeEnum type;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
