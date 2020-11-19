package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Project;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * ��Ŀ���ݷ�����
 */
public class ProjectDao {
	/**
	 * ����
	 */
	public void insert(Project project) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into project(type,name,about)values(?,?,?)";
		Object[] params = { project.getType(), project.getName(), project.getAbout() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from project where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void update(Project project) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update project set type=?,name=?,about=? where id =?";
		Object[] params = { project.getType(), project.getName(), project.getAbout(), project.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
	 */
	public Project getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from project where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		Project project = (Project) runner.query(conn, sql, new BeanHandler<Project>(Project.class), params);
		ConnectionUtils.releaseConnection(conn);
		return project;
	}

	/**
	 * ��ȡȫ��
	 */
	public List<Project> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from project ";
		QueryRunner runner = new QueryRunner();
		List<Project> projects = runner.query(conn, sql, new BeanListHandler<Project>(Project.class));
		ConnectionUtils.releaseConnection(conn);
		return projects;
	}

	/**
	 * ��ȡ����
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from project ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ
	 */
	public List<Project> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from project limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<Project> projects = runner.query(conn, sql, new BeanListHandler<Project>(Project.class), params);
		ConnectionUtils.releaseConnection(conn);
		return projects;
	}
}
