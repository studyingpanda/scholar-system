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
import org.maoge.scholar.dao.ProjectDao;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Result;
import org.maoge.scholar.model.User;

import com.alibaba.fastjson.JSON;

@WebServlet("/CoreServlet")
public class CoreServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置输入输出格式、编码
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter(); // 获取writer方法，用于将数据返回给ajax
		// 获取用户在网页输入的用户名和密码
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
	 * 具体处理请求
	 */
	public Result handleRequest(HttpServletRequest request) throws Exception {
		Result result = new Result();
		String method = request.getParameter("method");
		// 请求页码、每页显示行数、偏移、总数
		int page = 1, rows = 10, total = 0;
		if (method.contains("Page") == true) {
			// 获取
			String inputPage = request.getParameter("page");
			String inputRows = request.getParameter("rows");
			page = (inputPage == null) ? 1 : Integer.parseInt(inputPage);
			rows = (inputRows == null) ? 10 : Integer.parseInt(inputRows);
		}
		// 登录处理
		if (method.equals("login")) {
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
		}
		// 查询登录用户拥有的菜单
		else if (method.equals("getMenusOfUser")) {
			User loginUser = (User) request.getSession().getAttribute("loginUser");
			if (loginUser == null) {
				throw new Exception("未登录！");
			}
			MenuDao menuDao = new MenuDao();
			result.setData(menuDao.getMenusOfUser(loginUser));// 返回数据为对应菜单
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取项目分页
		else if (method.equals("getProjectPage")) {
			ProjectDao projectDao = new ProjectDao();
			total = projectDao.getCount();
			result.setTotal(total);
			result.setRows(projectDao.getPage(page, rows));
		}
		return result;
	}
}