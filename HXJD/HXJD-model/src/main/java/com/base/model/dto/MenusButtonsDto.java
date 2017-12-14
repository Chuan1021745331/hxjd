package com.base.model.dto;

import java.io.Serializable;

public class MenusButtonsDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id ;
	private String name;
	private String code;
	private boolean tf;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isTf() {
		return tf;
	}
	public void setTf(boolean tf) {
		this.tf = tf;
	}
}
