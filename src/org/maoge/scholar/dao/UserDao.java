package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.User;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * �û����ݷ�����
 */
public class UserDao {
	/**
	 * ����
	 */
	public void insert(User user) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into user(userName,loginName,password,role,departId)values(?,?,?)";
		Object[] params = { user.getUserName(), user.getLoginName(), user.getPassword(), user.getRole(),
				user.getDepartId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from user where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void update(User user) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update user set userName=?,loginName=?,password=?,role=?,departId=? where id =?";
		Object[] params = { user.getUserName(), user.getLoginName(), user.getPassword(), user.getRole(),
				user.getDepartId(), user.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
	 */
	public User getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from user where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		User user = (User) runner.query(conn, sql, new BeanHandler<User>(User.class), params);
		ConnectionUtils.releaseConnection(conn);
		return user;
	}

	/**
	 * ��ȡȫ��
	 */
	public List<User> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from user ";
		QueryRunner runner = new QueryRunner();
		List<User> users = runner.query(conn, sql, new BeanListHandler<User>(User.class));
		ConnectionUtils.releaseConnection(conn);
		return users;
	}

	/**
	 * ��ȡ����
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from user ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ
	 */
	public List<User> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from user limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<User> users = runner.query(conn, sql, new BeanListHandler<User>(User.class), params);
		ConnectionUtils.releaseConnection(conn);
		return users;
	}
}
