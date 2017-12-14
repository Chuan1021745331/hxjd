package com.base.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class JHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if (target.startsWith("/websocket")) {
			return;
		}
		String BPATH = request.getContextPath();
//		String BPATH = request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath();
		request.setAttribute("_request", request);
		request.setAttribute("BPATH", BPATH);
		request.setAttribute("CPATH", BPATH + "/common");
		request.setAttribute("APATH", BPATH + "/attachment");
		next.handle(target, request, response, isHandled);
	}
}
