package com.base.constants;

/**
 * 枚举
 * @author z
 *
 */
public enum NettyEnum {
	/**
	 * 0,"请求"
	 */
	HANDLE_REQUEST	(0,"请求"),
	/**
	 * 1,"响应"
	 */
	HANDLE_RESPONSE	(1,"响应"),
	/**
	 * 0,"失败"
	 */
	SUCCESS_FALSE	(0,"失败"),
	/**
	 * 1,"成功"
	 */
	SUCCESS_TRUE	(1,"成功"),
	/**
	 * 0,"心跳"
	 */
	OPERATION_HEARTBEAT			(0,"心跳"),
	/**
	 * 1,"设备初始化"
	 */
	OPERATION_DEVICE_INIT		(1,"设备初始化"),
	/**
	 * 2,"设备登录"
	 */
	OPERATION_DEVICE_LOGIN		(2,"设备登录"),
	/**
	 * 3,"获取设备信息"
	 */
	OPERATION_DEVICE_INFO		(3,"获取设备信息"),
	/**
	 * 4,"上传设备信息"
	 */
	OPERATION_DEVICE_INFO_PUT	(4,"上传设备信息"),
	/**
	 * 5,"添加图元"
	 */
	OPERATION_MAP_ADD			(5,"添加图元"),
	/**
	 * 100,"获取自身席位"
	 */
	OPERATION_SEAT_ONESELF		(100,"获取自身席位"),
	/**
	 * 101,"获取全部席位"
	 */
	OPERATION_SEAT_ALL			(101,"获取全部席位"),
	/**
	 * 102,"获取标注点"
	 */
	OPERATION_MARKER_ALL		(102,"获取标注点"),
	/**
	 * 103,"获取服务器时间"
	 */
	OPERATION_SERVER_TIME		(103,"获取服务器时间"),
	/**
	 * 201,"下发设备信息"
	 */
	OPERATION_SEND_DEVICE_INFO	(201,"下发设备信息"),
	/**
	 * 202,"下发自身席位信息"
	 */
	OPERATION_SEND_SEAT_ONESELF	(202,"下发自身席位信息"),
	/**
	 * 203,"下发全部席位信息"
	 */
	OPERATION_SEND_SEAT_ALL		(203,"下发全部席位信息"),
	/**
	 * 204,"下发标注点"
	 */
	OPERATION_SEND_MARKER_ALL	(204,"下发标注点"),
	/**
	 * 700,"非法操作"
	 */
	ERROR_UNKNOWN				(700,"非法操作"),
	/**
	 * 701,"设备未注册"
	 */
	ERROR_DEVICE_UNKNOWN		(701,"设备未注册");
	private int code;
	private String mess;

	NettyEnum(int code,String mess) {
		this.code = code;
		this.mess = mess;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public String getMess() {
		return mess;
	}

	public void setMess(String mess) {
		this.mess = mess;
	}
}
