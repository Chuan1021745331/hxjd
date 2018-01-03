package com.base.service;

import com.base.model.JUser;

public class UserServie {
	private static final UserServie SERVICE = new UserServie();
	public static UserServie me() {
		return SERVICE;
	}
	public JUser findById(final Integer roleId) {
		return UserQuery.me().findById(roleId);
	}
}
