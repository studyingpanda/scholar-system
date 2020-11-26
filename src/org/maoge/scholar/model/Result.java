package org.maoge.scholar.model;

/**
 * 统一返回结果类
 */
public class Result<T> {
	/**
	 * 0成功 9999失败
	 */
	private int code;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 返回数据
	 */
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
