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
import org.maoge.scholar.dao.FlowDao;
import org.maoge.scholar.dao.MenuDao;
import org.maoge.scholar.dao.ProjectDao;
import org.maoge.scholar.dao.QuestionDao;
import org.maoge.scholar.dao.RoleMenuDao;
import org.maoge.scholar.dao.UserDao;
import org.maoge.scholar.model.Depart;
import org.maoge.scholar.model.Flow;
import org.maoge.scholar.model.Menu;
import org.maoge.scholar.model.Project;
import org.maoge.scholar.model.Question;
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
			UserDao userDao = new UserDao();
			total = userDao.getCount();
			result.setTotal(total);
			result.setRows(userDao.getPage(page, rows));
		}
		// 新增人员保存
		else if (method.equals("addUser")) {
			UserDao userDao = new UserDao();
			User user = new User();
			user.setUserName(request.getParameter("userName"));
			user.setLoginName(request.getParameter("loginName"));
			user.setRole(request.getParameter("role"));
			user.setDepartId(request.getParameter("departId"));
			user.setPassword("123456");
			userDao.insert(user);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 编辑人员保存
		else if (method.equals("editUser")) {
			UserDao userDao = new UserDao();
			User user = new User();
			user.setId(request.getParameter("id"));
			user.setUserName(request.getParameter("userName"));
			user.setLoginName(request.getParameter("loginName"));
			user.setRole(request.getParameter("role"));
			user.setDepartId(request.getParameter("departId"));
			userDao.update(user);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 删除用户
		else if (method.equals("removeUser")) {
			UserDao userDao = new UserDao();
			userDao.deleteById(request.getParameter("id"));
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取权限分页
		else if (method.equals("getMenusByRole")) {
			RoleMenuDao roleMenuDao = new RoleMenuDao();
			List<Menu> menus = roleMenuDao.getMenusByRole(request.getParameter("role"));
			result.setCode(0);
			result.setRows(menus);
			result.setTotal(menus.size());
		}
		// 提交权限设置
		else if (method.equals("submitMenusByRole")) {
			String role = request.getParameter("role");
			String[] ids = request.getParameter("ids").split(",");

			RoleMenuDao roleMenuDao = new RoleMenuDao();
			roleMenuDao.submitMenusByRole(role, ids);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取问题列表
		else if (method.equals("getQuestionPage")) {
			if (loginUser.getRole().equals("schoolmaster")) {// 管理员查看全部问题
				QuestionDao questionDao = new QuestionDao();
				total = questionDao.getCount();
				result.setTotal(total);
				result.setRows(questionDao.getPage(page, rows));
			} else if (loginUser.getRole().equals("student")) {// 学生查看自己提交的问题
				QuestionDao questionDao = new QuestionDao();
				total = questionDao.getCount(loginUser.getId());
				result.setTotal(total);
				result.setRows(questionDao.getPage(page, rows, loginUser.getId()));
			}
		}
		// 提交问题
		else if (method.equals("questionSubmit")) {
			// 获取机构信息
			DepartDao departDao = new DepartDao();
			Depart depart = departDao.getById(loginUser.getDepartId());
			// 组装问题信息
			Question question = new Question();
			question.setUserId(loginUser.getId());
			question.setUserName(loginUser.getUserName());
			question.setDepartId(depart.getId());
			question.setDepartName(depart.getName());
			question.setContent(request.getParameter("content"));
			// 插入数据库
			QuestionDao questionDao = new QuestionDao();
			questionDao.insert(question);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 问题反馈
		else if (method.equals("editQuestion")) {
			QuestionDao questionDao = new QuestionDao();
			Question question = new Question();
			question.setId(request.getParameter("id"));
			question.setUserId(request.getParameter("userId"));
			question.setUserName(request.getParameter("userName"));
			question.setDepartId(request.getParameter("departId"));
			question.setDepartName(request.getParameter("departName"));
			question.setContent(request.getParameter("content"));
			question.setReply(request.getParameter("reply"));
			questionDao.update(question);
			result.setCode(0);
			result.setMsg("操作成功");
		}
		// 获取项目列表
		else if (method.equals("getProjectList")) {
			ProjectDao projectDao = new ProjectDao();
			result.setCode(0);
			result.setData(projectDao.getAll());
		}
		// 获取学生申请流程列表
		else if (method.equals("getStudentApplyFlowPage")) {
			FlowDao flowDao = new FlowDao();
			total = flowDao.getCountByStudentId(loginUser.getId());
			result.setTotal(total);
			result.setRows(flowDao.getPageByStudentId(page, rows, loginUser.getId()));
		}
		// 学生申请提交
		else if (method.equals("applySubmit")) {
			// 获取网页提交的信息
			String projectId = request.getParameter("projectId");
			String projectName = request.getParameter("projectName");
			String content = request.getParameter("content");
			// 获取管理员
			UserDao userDao = new UserDao();
			User classMaster = userDao.getClassMaster(loginUser);
			User collegeMaster = userDao.getCollegeMaster(loginUser);
			User schoolMaster = userDao.getSchoolMaster();
			// 新增
			FlowDao flowDao = new FlowDao();
			Flow flow = new Flow();
			flow.setStudentId(loginUser.getId());
			flow.setStudentName(loginUser.getUserName());
			flow.setProjectId(projectId);
			flow.setProjectName(projectName);
			flow.setContent(content);
			flow.setClassUserId(classMaster.getId());
			flow.setClassAdvice("");
			flow.setCollegeUserId(collegeMaster.getId());
			flow.setCollegeAdvice("");
			flow.setSchoolUserId(schoolMaster.getId());
			flow.setSchoolAdvice("");
			flow.setCurrentUserId(classMaster.getId());
			flow.setCurrentNode("class");
			flowDao.insert(flow);
			result.setCode(0);
			result.setMsg("操作成功");

		}
		return result;
	}
}