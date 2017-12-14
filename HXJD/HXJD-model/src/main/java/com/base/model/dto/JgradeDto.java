package com.base.model.dto;

import com.base.model.JGrademark;
import com.base.model.JKpi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JgradeDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JGrademark m;
	private Integer n = 0;
	private List<JgradeDto> children = new ArrayList<JgradeDto>();

	public JGrademark getM() {
		return m;
	}

	public void setM(JGrademark m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public List<JgradeDto> getChildren() {
		return children;
	}

	public void setChildren(List<JgradeDto> children) {
		this.children = children;
	}
}
