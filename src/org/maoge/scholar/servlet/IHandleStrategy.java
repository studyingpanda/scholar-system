package org.maoge.scholar.servlet;

import javax.servlet.http.HttpServletRequest;

import org.maoge.scholar.model.Result;

/**
 * ��������Խӿ�
 */
public interface IHandleStrategy {
	public Result execute(HttpServletRequest request) throws Exception;
}
