package com.qrqy.mysql.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import com.qrqy.dp.entity.CommonEntity;

/**
 * @author : Luis
 * @date : 2021-06-16 15:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_system_modular")
@EntityListeners(AuditingEntityListener.class)
public class AdminSystemModularEntity extends CommonEntity implements Serializable{

    private static final long serialVersionUID = 8018064634315668672L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	/**
	 * 管理员id
	 */
    @Column(name = "admin_id")
    private Integer adminId;
	/**
	 * 模块id组
	 */
   	@Column(name = "system_modular_ids")
    private String systemModularIds;
	/**
	 * 粘性模块组
	 */
   	@Column(name = "system_modular_sticker_ids")
    private String systemModularStickerIds;
	/**
	 * 状态
	 */
   	@Column(name = "status")
    private String status;

}
