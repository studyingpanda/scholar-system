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
