package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * route : admin-query
 * @author : QRQY
 * @date : 2021-06-11 09:33
 */
@Data
public class AdminListDTO implements IBaseDTO, Serializable {
    private static final long serialVersionUID = 2063976851936075468L;

	private Integer id;


	private String name;


	private String phonenum;


	private String position;

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;


	private String status;





}
