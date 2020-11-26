package org.maoge.scholar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maoge.scholar.dao.MenuDao;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Result;
import org.maoge.scholar.model.User;

import com.alibaba.fastjson.JSON;

@WebServlet("/CoreServlet")
public class CoreServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �������������ʽ������
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter(); // ��ȡwriter���������ڽ����ݷ��ظ�ajax
		// ��ȡ�û�����ҳ������û���������
		Result result = null;
		try {
			result = handleRequest(request);
		} catch (Exception ex) {
			result = new Result();
			result.setCode(9999);
			result.setMsg(ex.getMessage());
		} finally {
			out.write(JSON.toJSONString(result));
			out.flush();
			out.close();
		}
	}

	/**
	 * ���崦������
	 */
	public Result handleRequest(HttpServletRequest request) throws Exception {
		Result result = new Result();
		String method = request.getParameter("method");
		// ��¼����
		if (method.equals("login")) {
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
		}
		// ��ѯ��¼�û�ӵ�еĲ˵�
		else if (method.equals("getMenusOfUser")) {
			User loginUser = (User) request.getSession().getAttribute("loginUser");
			if (loginUser == null) {
				throw new Exception("δ��¼��");
			}
			MenuDao menuDao = new MenuDao();
			result.setData(menuDao.getMenusOfUser(loginUser));// ��������Ϊ��Ӧ�˵�
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// ����������ڴ˴�
		return result;
	}
}