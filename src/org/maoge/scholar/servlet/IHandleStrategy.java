package org.maoge.scholar.servlet;

import javax.servlet.http.HttpServletRequest;

import org.maoge.scholar.model.Result;

/**
 * 请求处理策略接口
 */
public interface IHandleStrategy {
	public Result execute(HttpServletRequest request) throws Exception;
}
