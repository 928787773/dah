package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.BaseEntity;

/**
 * @author : Luis
 * @date : 2021-06-25 14:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "treatment_intervent")
@EntityListeners(AuditingEntityListener.class)
public class TreatmentInterventEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 2929059092345505914L;

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
	 * 状态
	 */
   	@Column(name = "status")
    private String status;
	/**
	 * 排序
	 */
   	@Column(name = "sort")
    private Integer sort;

}
