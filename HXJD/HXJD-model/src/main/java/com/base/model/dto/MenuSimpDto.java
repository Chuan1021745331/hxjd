package com.base.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuSimpDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String score;

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	private List<MenuSimpDto> children = new ArrayList<MenuSimpDto>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuSimpDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuSimpDto> children) {
		this.children = children;
	}
}
