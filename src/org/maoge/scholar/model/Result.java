package org.maoge.scholar.model;

/**
 * ͳһ���ؽ����
 */
public class Result<T> {
	/**
	 * 0�ɹ� 9999ʧ��
	 */
	private int code;
	/**
	 * ��ʾ��Ϣ
	 */
	private String msg;
	/**
	 * ��������
	 */
	private T data;
	/**
	 * ��ҳ����
	 */
	private int total;
	/**
	 * ��ҳ����
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
