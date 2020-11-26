package org.maoge.scholar.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.User;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * 用户数据访问类
 */
public class UserDao {

	/**
	 * 通过登录名、密码获取用户
	 */
	public List<User> getUsersByLoginNameAndPassword(String loginName, String password) throws SQLException {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from user where loginName=? and password=?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { loginName, password };
		List<User> users = runner.query(conn, sql, new BeanListHandler<User>(User.class), params);
		ConnectionUtils.releaseConnection(conn);
		return users;
	}

	/**
	 * 新增
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
	 * 移除
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
	 * 更新
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
	 * 获取一个
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
	 * 获取全部
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
	 * 获取数量
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
	 * 分页查询
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
