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
		// ����ҳ�롢ÿҳ��ʾ����������
		int page = 1, rows = 10, total = 0;
		if (method.contains("Page") == true) {// ��ǰΪ��ҳ����
			// ��ȡ
			String inputPage = request.getParameter("page");
			String inputRows = request.getParameter("rows");
			page = (inputPage == null) ? 1 : Integer.parseInt(inputPage);
			rows = (inputRows == null) ? 10 : Integer.parseInt(inputRows);
		}
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
		// ��ȡ��Ŀ��ҳ
		else if (method.equals("getProjectPage")) {
			ProjectDao projectDao = new ProjectDao();
			total = projectDao.getCount();
			result.setTotal(total);
			result.setRows(projectDao.getPage(page, rows));
		}
		// ������Ŀ����
		else if (method.equals("addProject")) {
			ProjectDao projectDao = new ProjectDao();
			Project project = new Project();
			project.setType(request.getParameter("type"));
			project.setName(request.getParameter("name"));
			project.setAbout(request.getParameter("about"));
			projectDao.insert(project);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// �༭��Ŀ����
		else if (method.equals("editProject")) {
			ProjectDao projectDao = new ProjectDao();
			Project project = new Project();
			project.setId(request.getParameter("id"));
			project.setType(request.getParameter("type"));
			project.setName(request.getParameter("name"));
			project.setAbout(request.getParameter("about"));
			projectDao.update(project);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// ��ȡ�����б�
		else if (method.equals("getDepartList")) {
			DepartDao departDao = new DepartDao();
			result.setCode(0);
			result.setData(departDao.getAll());
		}
		// ��ȡ������ҳ
		else if (method.equals("getDepartPage")) {
			DepartDao departDao = new DepartDao();
			total = departDao.getCount();
			result.setTotal(total);
			result.setRows(departDao.getPage(page, rows));
		}
		// ������������
		else if (method.equals("addDepart")) {
			DepartDao departDao = new DepartDao();
			Depart depart = new Depart();
			depart.setType(request.getParameter("type"));
			depart.setName(request.getParameter("name"));
			depart.setParentId(request.getParameter("parentId"));
			departDao.insert(depart);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// �༭��������
		else if (method.equals("editDepart")) {
			DepartDao departDao = new DepartDao();
			Depart depart = new Depart();
			depart.setId(request.getParameter("id"));
			depart.setType(request.getParameter("type"));
			depart.setName(request.getParameter("name"));
			depart.setParentId(request.getParameter("parentId"));
			departDao.update(depart);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		return result;
	}
}