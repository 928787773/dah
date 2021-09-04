package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.BaseEntity;

/**
 * @author : Luis
 * @date : 2021-06-25 14:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "disease_type")
@EntityListeners(AuditingEntityListener.class)
public class DiseaseTypeEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 3132521456052023727L;

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

   	@Column(name = "sort")
    private Integer sort;

   	@Column(name = "status")
    private String status;

}
