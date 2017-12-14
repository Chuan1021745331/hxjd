package com.base.core;

import java.math.BigInteger;

import javax.servlet.http.HttpSession;

import com.base.render.AjaxResult;
import com.base.render.PageResult;
import com.base.render.JCaptchaRender;
import com.base.render.LayPageResult;
import com.base.utils.JsoupUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionException;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.interceptor.NotAction;
import com.jfinal.i18n.Res;

public class BaseController extends Controller {
	private static final char URL_PARA_SEPARATOR = JFinal.me().getConstants().getUrlParaSeparator().toCharArray()[0];
	private JSession session;

	public BaseController() {
		session = new JSession(this);
	}

	@Override
	public String getPara(String name) {
		return JsoupUtils.clear(getRequest().getParameter(name));
	}

	@Override
	public String getPara(String name, String defaultValue) {
		String html = getRequest().getParameter(name);
		if (null != html) {
			return JsoupUtils.clear(html);
		}
		return defaultValue;
	}

	private int mParaCount = -1;

	public int getParaCount() {
		if (mParaCount != -1) {
			return mParaCount;
		}

		mParaCount = 0;
		char[] parachars = getPara() == null ? null : getPara().toCharArray();
		if (parachars != null) {
			mParaCount = 1;
			for (char c : parachars) {
				if (URL_PARA_SEPARATOR == c) {
					mParaCount++;
				}
			}
		}
		return mParaCount;
	}

	public boolean isAjaxRequest() {
		String header = getRequest().getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(header);
	}

	public boolean isMultipartRequest() {
		String contentType = getRequest().getContentType();
		return contentType != null && contentType.toLowerCase().indexOf("multipart") != -1;
	}

	protected int getPageNumbere() {
		int page = getParaToInt("page", 1);
		if (page < 1) {
			page = 1;
		}
		return page;
	}

	protected int getPageSize() {
		int size = getParaToInt("size", 10);
		if (size < 1) {
			size = 1;
		}
		return size;
	}

	public void setHeader(String key, String value) {
		getResponse().setHeader(key, value);
	}

	public Res getI18nRes() {
		// Attribute set in JI18nInterceptor.class
		return getAttr("i18n");
	}

	public String getI18nValue(String key) {
		return getI18nRes().get(key);
	}

	@Before(NotAction.class)
	public void renderAjaxResultForSuccess() {
		renderAjaxResult("success", 0, null);
	}

	public void renderAjaxResultForSuccess(String message) {
		renderAjaxResult(message, 0, null);
	}

	@Before(NotAction.class)
	public void renderAjaxResultForError() {
		renderAjaxResult("error", 1, null);
	}

	public void renderAjaxResultForError(String message) {
		renderAjaxResult(message, 1, null);
	}

	public void renderAjaxResult(String message, int errorCode) {
		renderAjaxResult(message, errorCode, null);
	}

	public void renderAjaxResult(String message, int errorCode, Object data) {
		AjaxResult ar = new AjaxResult();
		ar.setMessage(message);
		ar.setErrorCode(errorCode);
		ar.setData(data);

		// if (isIEBrowser()) {
		// render(new JsonRender(ar).forIE());
		// } else {
		renderJson(ar);
		// }

	}

	public void renderPageResult(int draw, Integer recordsTotal, Integer recordsFiltered, Object data) {
		PageResult ar = new PageResult();
		ar.setDraw(draw);
		ar.setRecordsTotal(recordsTotal);
		ar.setRecordsFiltered(recordsFiltered);
		ar.setData(data);

		// if (isIEBrowser()) {
		// render(new JsonRender(ar).forIE());
		// } else {
		renderJson(ar);
		// }

	}

	public void renderPageResult(int code, String msg, int count, Object data) {
		LayPageResult ar = new LayPageResult();
		ar.setCode(code);
		ar.setMsg(msg);
		ar.setCount(count);
		ar.setData(data);

		// if (isIEBrowser()) {
		// render(new JsonRender(ar).forIE());
		// } else {
		renderJson(ar);
		// }

	}

	public boolean isIEBrowser() {
		String ua = getRequest().getHeader("User-Agent").toLowerCase();
		if (ua != null && ua.indexOf("msie") > 0) {
			return true;
		}

		if (ua != null && ua.indexOf("gecko") > 0 && ua.indexOf("rv:11") > 0) {
			return true;
		}

		return false;
	}

	@Override
	@Before(NotAction.class)
	public void createToken() {
		createToken("jtoken");
	}

	@Override
	public boolean validateToken() {
		return validateToken("jtoken");
	}

	@Override
	public HttpSession getSession() {
		return session;
	}

	@Override
	public HttpSession getSession(boolean create) {
		return getSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSessionAttr(String key) {
		return (T) session.getAttribute(key);
	}

	@Override
	public Controller setSessionAttr(String key, Object value) {
		session.setAttribute(key, value);
		return this;
	}

	@Override
	public Controller removeSessionAttr(String key) {
		session.removeAttribute(key);
		return this;
	}

	@Override
	@Before(NotAction.class)
	public void renderCaptcha() {
		render(new JCaptchaRender(this));
	}

	@Override
	public boolean validateCaptcha(String paraName) {
		return JCaptchaRender.validate(this, getPara(paraName));
	}

	public BigInteger[] getParaValuesToBigInteger(String name) {
		String[] values = getRequest().getParameterValues(name);
		if (values == null) {
			return null;
		}
		BigInteger[] result = new BigInteger[values.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new BigInteger(values[i]);
		}
		return result;
	}

	public BigInteger getParaToBigInteger(String name) {
		return toBigInteger(getRequest().getParameter(name), null);
	}

	public BigInteger getParaToBigInteger(String name, BigInteger defaultValue) {
		return toBigInteger(getRequest().getParameter(name), defaultValue);
	}

	public void renderTable(String page) {
		setAttr("tableBase", "page:true, skin:'row', even:true,limits:[10,30,50,100,200], limit:30,size: 'sm'");
		setAttr("thBase", "event: 'setSign', style:'cursor: pointer;'");
		render(page);
	}

	private BigInteger toBigInteger(String value, BigInteger defaultValue) {
		try {
			if (value == null || "".equals(value.trim())) {
				return defaultValue;
			}
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n")) {
				return new BigInteger(value).negate();
			}
			return new BigInteger(value);
		} catch (Exception e) {
			throw new ActionException(404, "Can not parse the parameter \"" + value + "\" to BigInteger value.");
		}
	}

	@Before(NotAction.class)
	public String getIPAddress() {
		String ip = getRequest().getHeader("X-getRequest()ed-For");
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("X-Forwarded-For");
		}
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("Proxy-Client-IP");
		}
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("HTTP_CLIENT_IP");
		}
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (!StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = getRequest().getRemoteAddr();
		}
		return ip;
	}

	@Before(NotAction.class)
	public String getUserAgent() {
		return getRequest().getHeader("User-Agent");
	}

	public static void main(String[] args) {
		System.out.println(630 % 15);
	}

	public void page(String upPage, String downPage, String dirPage, int allCount, int pageSize) {
		int allPage = allCount / pageSize <= 0 ? 1
				: allCount % pageSize > 0 ? allCount / pageSize + 1 : allCount / pageSize;
		setAttr("allPage", allPage);
		if (!StringUtils.isNotEmpty(upPage) && !StringUtils.isNotEmpty(downPage)) {
			setAttr("upPage", 1);
			if (allCount / pageSize > 0) {
				setAttr("downPage", 2);
			} else {
				setAttr("downPage", 1);
			}
		} else {
			int up = Integer.parseInt(upPage);
			int dp = Integer.parseInt(downPage);
			if ("u".equals(dirPage)) {
				setAttr("upPage", up == 1 ? 1 : up - 1 < 1 ? 1 : up - 1);
				setAttr("downPage", dp == 1 ? 1 : dp - 1 < 2 ? 2 : dp - 1);
			} else {
				setAttr("upPage", up + 1);
				setAttr("downPage", dp + 1);
			}

		}
	}

}
