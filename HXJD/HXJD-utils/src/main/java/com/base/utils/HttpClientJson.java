package com.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

public class HttpClientJson {
	
	/**
	 * http post请求
	 * 
	 * @param url 请求地址
	 * @param data 请求参数
	 * @return 返回json数据
	 */
	public JSONObject doPostJson(String url, NameValuePair[] data) {
		// 创建post方法实例
		PostMethod postMethod = new PostMethod(url);
		// 在头文件中设置转码
		postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 将表单的值放入到post方法中
		postMethod.setRequestBody(data);
		// 提供了兼容性的高度政策
		postMethod.getParams().setParameter(HttpMethodParams.COOKIE_POLICY,CookiePolicy.BROWSER_COMPATIBILITY);
		return sendRequest(postMethod);
	}

	/**
	 * http get请求
	 * 
	 * @param url 请求地址
	 * @return 返回json数据
	 */
	public JSONObject doGetJson(String url) {
		// 创建get方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 在头文件中设置转码
		getMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		// 使用系统提供的默认恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		return sendRequest(getMethod);
	}
	
	/**
	 * 发送http请求
	 * 
	 * @param method
	 * @return
	 */
	private JSONObject sendRequest(HttpMethodBase method){
		// 定义httpClient的实例
		HttpClient httpclient = new HttpClient();
		InputStream responseBody = null;
		BufferedReader reader = null;
		JSONObject jsonObject = null;
		try {
			httpclient.executeMethod(method);
			responseBody = method.getResponseBodyAsStream();
			reader = new BufferedReader(new InputStreamReader(responseBody, "UTF-8"));
			jsonObject = bufferedToJson(reader);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			StreamClose.bufferedReaderClose(reader);
			StreamClose.inputStreamClose(responseBody);
			method.releaseConnection();// 释放链接
		}
		return jsonObject;
	}
	
	/**
	 * 字节流转json
	 * 
	 * @param reader
	 * @return
	 */
	private JSONObject bufferedToJson(BufferedReader reader){
		StringBuffer sb = new StringBuffer();
		JSONObject jsonObject = null;
		try {
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			jsonObject = JSONObject.parseObject(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			StreamClose.bufferedReaderClose(reader);
		}
		return jsonObject;
	}
}
