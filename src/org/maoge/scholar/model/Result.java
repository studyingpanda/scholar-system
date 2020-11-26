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
	/**
	 * 分页总数
	 */
	private int total;
	/**
	 * 分页数据
	 */
	private T rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

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
