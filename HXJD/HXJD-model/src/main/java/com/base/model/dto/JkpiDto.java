package com.base.model.dto;

import com.base.model.JKpi;
import com.base.model.JMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JkpiDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JKpi m;
	private Integer n = 0;
	private List<JkpiDto> children = new ArrayList<JkpiDto>();

	public JKpi getM() {
		return m;
	}

	public void setM(JKpi m) {
		this.m = m;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public List<JkpiDto> getChildren() {
		return children;
	}

	public void setChildren(List<JkpiDto> children) {
		this.children = children;
	}
}
