package com.base.model.dto;

import java.io.Serializable;
import java.util.List;

import com.base.model.JButton;
import com.base.model.JMenu;

public class MenuButton implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu menu;
	private List<JButton> button;
	public JMenu getMenu() {
		return menu;
	}
	public void setMenu(JMenu menu) {
		this.menu = menu;
	}
	public List<JButton> getButton() {
		return button;
	}
	public void setButton(List<JButton> button) {
		this.button = button;
	}
}
