package org.maoge.scholar.servlet;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Result;
import org.maoge.scholar.model.User;

/**
 * 登录请求处理策略
 */
public class LoginHandleStrategy implements IHandleStrategy {
	@Override
	public Result execute(HttpServletRequest request) throws Exception {
		Result result = new Result();
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		UserDao userDao = new UserDao();
		List<User> users = userDao.getUsersByLoginNameAndPassword(loginName, password);
		if (users != null && users.size() == 1) {// 登录成功
			result.setCode(0);
			result.setMsg("操作成功");
			result.setData(users.get(0));
			request.getSession().setAttribute("loginUser", users.get(0));
		} else {// 登录失败
			result.setCode(9999);
			result.setMsg("操作失败");
		}
		return result;
	}
}
