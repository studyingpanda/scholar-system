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
			System.err.println(ex.getMessage());
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
		// ���δ��¼���Ҳ�������ĵ�¼������ֱ���׳��쳣
		User loginUser = (User) request.getSession().getAttribute("loginUser");
		if (loginUser == null && !method.equals("login")) {
			throw new Exception("δ��¼��");
		}
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
		// ��ȡ��Ա��ҳ
		else if (method.equals("getUserPage")) {
			UserDao userDao = new UserDao();
			total = userDao.getCount();
			result.setTotal(total);
			result.setRows(userDao.getPage(page, rows));
		}
		// ������Ա����
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
			result.setMsg("�����ɹ�");
		}
		// �༭��Ա����
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
			result.setMsg("�����ɹ�");
		}
		// ɾ���û�
		else if (method.equals("removeUser")) {
			UserDao userDao = new UserDao();
			userDao.deleteById(request.getParameter("id"));
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// ��ȡȨ�޷�ҳ
		else if (method.equals("getMenusByRole")) {
			RoleMenuDao roleMenuDao = new RoleMenuDao();
			List<Menu> menus = roleMenuDao.getMenusByRole(request.getParameter("role"));
			result.setCode(0);
			result.setRows(menus);
			result.setTotal(menus.size());
		}
		// �ύȨ������
		else if (method.equals("submitMenusByRole")) {
			String role = request.getParameter("role");
			String[] ids = request.getParameter("ids").split(",");

			RoleMenuDao roleMenuDao = new RoleMenuDao();
			roleMenuDao.submitMenusByRole(role, ids);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// ��ȡ�����б�
		else if (method.equals("getQuestionPage")) {
			if (loginUser.getRole().equals("schoolmaster")) {// ����Ա�鿴ȫ������
				QuestionDao questionDao = new QuestionDao();
				total = questionDao.getCount();
				result.setTotal(total);
				result.setRows(questionDao.getPage(page, rows));
			} else if (loginUser.getRole().equals("student")) {// ѧ���鿴�Լ��ύ������
				QuestionDao questionDao = new QuestionDao();
				total = questionDao.getCount(loginUser.getId());
				result.setTotal(total);
				result.setRows(questionDao.getPage(page, rows, loginUser.getId()));
			}
		}
		// �ύ����
		else if (method.equals("questionSubmit")) {
			// ��ȡ������Ϣ
			DepartDao departDao = new DepartDao();
			Depart depart = departDao.getById(loginUser.getDepartId());
			// ��װ������Ϣ
			Question question = new Question();
			question.setUserId(loginUser.getId());
			question.setUserName(loginUser.getUserName());
			question.setDepartId(depart.getId());
			question.setDepartName(depart.getName());
			question.setContent(request.getParameter("content"));
			// �������ݿ�
			QuestionDao questionDao = new QuestionDao();
			questionDao.insert(question);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		// ���ⷴ��
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
			result.setMsg("�����ɹ�");
		}
		// ��ȡ��Ŀ�б�
		else if (method.equals("getProjectList")) {
			ProjectDao projectDao = new ProjectDao();
			result.setCode(0);
			result.setData(projectDao.getAll());
		}
		// ��ȡѧ�����������б�
		else if (method.equals("getStudentApplyFlowPage")) {
			FlowDao flowDao = new FlowDao();
			total = flowDao.getCountByStudentId(loginUser.getId());
			result.setTotal(total);
			result.setRows(flowDao.getPageByStudentId(page, rows, loginUser.getId()));
		}
		// ѧ�������ύ
		else if (method.equals("applySubmit")) {
			// ��ȡ��ҳ�ύ����Ϣ
			String projectId = request.getParameter("projectId");
			String projectName = request.getParameter("projectName");
			String content = request.getParameter("content");
			// ��ȡ����Ա
			UserDao userDao = new UserDao();
			User classMaster = userDao.getClassMaster(loginUser);
			User collegeMaster = userDao.getCollegeMaster(loginUser);
			User schoolMaster = userDao.getSchoolMaster();
			// ����
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
			result.setMsg("�����ɹ�");
		}
		// ��ȡ��������¼
		else if (method.equals("getAuditPage")) {
			FlowDao flowDao = new FlowDao();
			total = flowDao.getCountByCurrentUserId(loginUser.getId());
			result.setTotal(total);
			result.setRows(flowDao.getPageByCurrentUserId(page, rows, loginUser.getId()));
		}
		// ����
		else if (method.equals("audit")) {
			FlowDao flowDao = new FlowDao();
			String flowId = request.getParameter("id");
			String state = request.getParameter("state");
			String advice = request.getParameter("advice");
			Flow flow = flowDao.getById(flowId);
			// �������
			if (loginUser.getRole().equals("classmaster")) {
				flow.setClassAdvice(advice);
			} else if (loginUser.getRole().equals("collegemaster")) {
				flow.setCollegeAdvice(advice);
			} else if (loginUser.getRole().equals("schoolmaster")) {
				flow.setSchoolAdvice(advice);
			}
			// ʧ��
			if (state.equals("fail")) {
				flow.setCurrentUserId(flow.getStudentId());
				flow.setCurrentNode("fail");
			} else {
				if (loginUser.getRole().equals("classmaster")) {
					flow.setCurrentUserId(flow.getCollegeUserId());
					flow.setCurrentNode("college");
				} else if (loginUser.getRole().equals("collegemaster")) {
					flow.setCurrentUserId(flow.getSchoolUserId());
					flow.setCurrentNode("school");
				} else if (loginUser.getRole().equals("schoolmaster")) {
					flow.setCurrentUserId(flow.getStudentId());
					flow.setCurrentNode("success");
				}
			}
			flowDao.update(flow);
			result.setCode(0);
			result.setMsg("�����ɹ�");
		}
		return result;
	}
}