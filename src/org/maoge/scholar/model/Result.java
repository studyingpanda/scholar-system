package org.maoge.scholar.model;

/**
 * 结果
 * 
 * @author Administrator
 *
 */
public class Result {
	private int code;
	private String msg;

	public static Result successResult() {
		Result result = new Result();
		result.setCode(0);
		result.setMsg("操作成功");
		return result;
	}

	public static Result failResult(String msg) {
		Result result = new Result();
		result.setCode(9999);
		result.setMsg(msg);
		return result;
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
