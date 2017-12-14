package com.base.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.base.model.JMenu;

public class MenuDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu m;
	private Integer n = 0;
	private List<MenuDto> children = new ArrayList<MenuDto>();

	public JMenu getM() {
		return m;
	}

	public void setM(JMenu m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public List<MenuDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}
}
