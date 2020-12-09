package org.maoge.scholar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.maoge.scholar.dao.DepartDao;
import org.maoge.scholar.dao.MenuDao;
import org.maoge.scholar.dao.ProjectDao;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Depart;
import org.maoge.scholar.model.Project;
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
			System.err.println(ex.getMessage());
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
		// 如果未登录，且不是请求的登录方法，直接抛出异常
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if (loginUser == null && !method.equals("login")) {
			throw new Exception("未登录！");
		}
		// 请求页码、每页显示行数、总数
		int page = 1, rows = 10, total = 0;
		if (method.contains("Page") == true) {// 当前为分页请求
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
		// 新增项目保存
		else if (method.equals("addProject")) {
			ProjectDao projectDao = new ProjectDao();
			Project project = new Project();
			project.setType(request.getParameter("type"));
			project.setName(request.getParameter("name"));
			project.setAbout(request.getParameter("about"));
			projectDao.insert(project);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 编辑项目保存
		else if (method.equals("editProject")) {
			ProjectDao projectDao = new ProjectDao();
			Project project = new Project();
			project.setId(request.getParameter("id"));
			project.setType(request.getParameter("type"));
			project.setName(request.getParameter("name"));
			project.setAbout(request.getParameter("about"));
			projectDao.update(project);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取机构列表
		else if (method.equals("getDepartList")) {
			DepartDao departDao = new DepartDao();
			result.setCode(0);
			result.setData(departDao.getAll());
		}
		// 获取机构分页
		else if (method.equals("getDepartPage")) {
			DepartDao departDao = new DepartDao();
			total = departDao.getCount();
			result.setTotal(total);
			result.setRows(departDao.getPage(page, rows));
		}
		// 新增机构保存
		else if (method.equals("addDepart")) {
			DepartDao departDao = new DepartDao();
			Depart depart = new Depart();
			depart.setType(request.getParameter("type"));
			depart.setName(request.getParameter("name"));
			depart.setParentId(request.getParameter("parentId"));
			departDao.insert(depart);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 编辑机构保存
		else if (method.equals("editDepart")) {
			DepartDao departDao = new DepartDao();
			Depart depart = new Depart();
			depart.setId(request.getParameter("id"));
			depart.setType(request.getParameter("type"));
			depart.setName(request.getParameter("name"));
			depart.setParentId(request.getParameter("parentId"));
			departDao.update(depart);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取人员分页
		else if (method.equals("getUserPage")) {
			String queryRole = "";
			if (loginUser.getRole().equals("schoolmaster")) {// 学校管理员，管理对象为学院管理员(所属机构为学校的下级机构)
				queryRole = "collegemaster";
			} else if (loginUser.getRole().equals("collegemaster")) {// 学院管理员，管理对象为本院的班主任(所属机构为学院的下级机构)
				queryRole = "classmaster";
			} else if (loginUser.getRole().equals("classmaster")) {// 班主任，管理对象为本班的学生(所属机构为班级的当前机构)
				queryRole = "student";
			}
			UserDao userDao = new UserDao();
			total = userDao.getCountByRoleAndDepart(queryRole, loginUser.getDepartId());
			result.setTotal(total);
			result.setRows(userDao.getPageByRoleAndDepart(page, rows, queryRole, loginUser.getDepartId()));
		}
		return result;
	}
}