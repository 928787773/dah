package com.qrqy.dah.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.qrqy.dp.dto.IBaseDTO;
import com.qrqy.dp.security.IBaseUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * route : common-login
 * @author : QRQY
 * @date : 2021-06-10 13:59
 */
@Data
public class CommonLoginDTO implements IBaseDTO, IBaseUser, Serializable {
    private static final long serialVersionUID = 7308345516386112584L;

	private Integer id;


	private String name;


	private String phonenum;


	private String token;


	@Override
	public Integer getUserId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return list;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@JSONField(serialize = false)
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@JSONField(serialize = false)
	@Override
	public boolean isEnabled() {
		return false;
	}
}
