package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.BaseEntity;

/**
 * @author : Luis
 * @date : 2021-06-25 14:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "country")
@EntityListeners(AuditingEntityListener.class)
public class CountryEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 8854451478037499401L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 简称
	 */
   	@Column(name = "short_name")
    private String shortName;
	/**
	 * 全称
	 */
   	@Column(name = "name")
    private String name;
	/**
	 * 英文名称
	 */
   	@Column(name = "en_name")
    private String enName;
	/**
	 * 经度
	 */
   	@Column(name = "lon")
    private String lon;
	/**
	 * 纬度
	 */
   	@Column(name = "lat")
    private String lat;
	/**
	 * code
	 */
   	@Column(name = "code")
    private String code;
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
