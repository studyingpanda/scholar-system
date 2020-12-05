package org.maoge.scholar.servlet;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Result;
import org.maoge.scholar.model.User;

/**
 * ��¼���������
 */
public class LoginHandleStrategy implements IHandleStrategy {
	@Override
	public Result execute(HttpServletRequest request) throws Exception {
		Result result = new Result();
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		UserDao userDao = new UserDao();
		List<User> users = userDao.getUsersByLoginNameAndPassword(loginName, password);
		if (users != null && users.size() == 1) {// ��¼�ɹ�
			result.setCode(0);
			result.setMsg("�����ɹ�");
			result.setData(users.get(0));
			request.getSession().setAttribute("loginUser", users.get(0));
		} else {// ��¼ʧ��
			result.setCode(9999);
			result.setMsg("����ʧ��");
		}
		return result;
	}
}
