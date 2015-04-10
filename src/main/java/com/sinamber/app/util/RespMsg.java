package com.sinamber.app.util;

/**
 * 请求返回消息
 * @Description:
 * @Author:Sine Chen
 * @Date:Oct 8, 2014
 * @Copyright: All Rights Reserved. Copyright(c) 2014
 */
public class RespMsg {
	private int status; // 0： 成功  1：失败   -1：服务器失败   
	private String msg;
	private Object data;

	public static RespMsg getInstance() {
		return new RespMsg();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "RespMsg [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}
}
