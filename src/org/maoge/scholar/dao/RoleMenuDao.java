package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.RoleMenu;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * 项目数据访问类
 */
public class RoleMenuDao {
	/**
	 * 新增
	 */
	public void insert(RoleMenu roleMenu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into roleMenu(roleId,menuId)values(?,?)";
		Object[] params = { roleMenu.getRoleId(), roleMenu.getMenuId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 移除
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from roleMenu where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 更新
	 */
	public void update(RoleMenu roleMenu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update roleMenu set roleId=?,menuId=? where id =?";
		Object[] params = { roleMenu.getRoleId(), roleMenu.getMenuId(), roleMenu.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 获取一个
	 */
	public RoleMenu getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from roleMenu where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		RoleMenu roleMenu = (RoleMenu) runner.query(conn, sql, new BeanHandler<RoleMenu>(RoleMenu.class), params);
		ConnectionUtils.releaseConnection(conn);
		return roleMenu;
	}

	/**
	 * 获取全部
	 */
	public List<RoleMenu> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from roleMenu ";
		QueryRunner runner = new QueryRunner();
		List<RoleMenu> roleMenus = runner.query(conn, sql, new BeanListHandler<RoleMenu>(RoleMenu.class));
		ConnectionUtils.releaseConnection(conn);
		return roleMenus;
	}

	/**
	 * 获取数量
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from roleMenu ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * 分页查询
	 */
	public List<RoleMenu> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from roleMenu limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<RoleMenu> roleMenus = runner.query(conn, sql, new BeanListHandler<RoleMenu>(RoleMenu.class), params);
		ConnectionUtils.releaseConnection(conn);
		return roleMenus;
	}
}
