package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Flow;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * �������ݷ�����
 */
public class FlowDao {
	/**
	 * ����
	 */
	public void insert(Flow flow) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into flow(studentId,studentName,projectId,projectName,content,classUserId,classAdvice,schoolUserId,schoolAdvice"
				+ ",currentUserId,currentNode)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { flow.getStudentId(), flow.getStudentName(), flow.getProjectId(), flow.getProjectName(),
				flow.getContent(), flow.getClassUserId(), flow.getClassAdvice(), flow.getCollegeUserId(),
				flow.getCollegeAdvice(), flow.getSchoolUserId(), flow.getSchoolUserId(), flow.getCurrentUserId(),
				flow.getCurrentNode() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from flow where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void update(Flow flow) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update flow set studentId=?,studentName=?,projectId=?,projectName=?"
				+ ",content=?,classUserId=?,classAdvice=?,collegeUserId=?,collegeAdvice=?,schoolUserId=?,schoolAdvice=?,"
				+ "currentUserId=?,currentNode=? where id =?";
		Object[] params = { flow.getStudentId(), flow.getStudentName(), flow.getProjectId(), flow.getProjectName(),
				flow.getContent(), flow.getClassUserId(), flow.getClassAdvice(), flow.getCollegeUserId(),
				flow.getCollegeAdvice(), flow.getSchoolUserId(), flow.getSchoolUserId(), flow.getCurrentUserId(),
				flow.getCurrentNode(), flow.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
	 */
	public Flow getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from flow where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		Flow flow = (Flow) runner.query(conn, sql, new BeanHandler<Flow>(Flow.class), params);
		ConnectionUtils.releaseConnection(conn);
		return flow;
	}

	/**
	 * ��ȡȫ��
	 */
	public List<Flow> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from flow ";
		QueryRunner runner = new QueryRunner();
		List<Flow> flows = runner.query(conn, sql, new BeanListHandler<Flow>(Flow.class));
		ConnectionUtils.releaseConnection(conn);
		return flows;
	}

	/**
	 * ��ȡ����
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from flow ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ
	 */
	public List<Flow> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from flow limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<Flow> flows = runner.query(conn, sql, new BeanListHandler<Flow>(Flow.class), params);
		ConnectionUtils.releaseConnection(conn);
		return flows;
	}
}
