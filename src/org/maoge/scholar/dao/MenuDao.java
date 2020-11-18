package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Menu;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * 菜单数据访问类
 */
public class MenuDao {
	/**
	 * 新增
	 */
	public void insert(Menu menu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into menu(name,path)values(?,?)";
		Object[] params = { menu.getName(), menu.getPath() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 移除
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from menu where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 更新
	 */
	public void update(Menu menu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update menu set name=?,path=? where id =?";
		Object[] params = { menu.getName(), menu.getPath(), menu.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * 获取一个
	 */
	public Menu getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from menu where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		Menu menu = (Menu) runner.query(conn, sql, new BeanHandler<Menu>(Menu.class), params);
		ConnectionUtils.releaseConnection(conn);
		return menu;
	}

	/**
	 * 获取全部
	 */
	public List<Menu> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from menu ";
		QueryRunner runner = new QueryRunner();
		List<Menu> menus = runner.query(conn, sql, new BeanListHandler<Menu>(Menu.class));
		ConnectionUtils.releaseConnection(conn);
		return menus;
	}

	/**
	 * 获取数量
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from menu ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * 分页查询
	 */
	public List<Menu> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from menu limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<Menu> menus = runner.query(conn, sql, new BeanListHandler<Menu>(Menu.class), params);
		ConnectionUtils.releaseConnection(conn);
		return menus;
	}
}
