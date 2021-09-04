package com.qrqy.dah.dto;

import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.mysql.entity.SystemModularEntity;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * route : admin-admin-system-modular-get
 * @author : QRQY
 * @date : 2021-06-25 11:43
 */
@Data
public class AdminSystemModularDetailDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 7511716738628941130L;

	private Integer id;


	private Integer adminId;


	private String systemModularIds;


	private String systemModularStickerIds;


	private List<SystemModularEntity> systemModularList;


}
