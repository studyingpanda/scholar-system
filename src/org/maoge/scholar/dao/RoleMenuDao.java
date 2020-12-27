package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Menu;
import org.maoge.scholar.model.RoleMenu;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * ��Ŀ���ݷ�����
 */
public class RoleMenuDao {

	/**
	 * ͨ����ɫ��ȡ�˵�
	 */
	public List<Menu> getMenusByRole(String role) throws Exception {
		// ȫ���˵�
		MenuDao menuDao = new MenuDao();
		List<Menu> allMenus = menuDao.getAll();
		// ��ɫ��Ӧ�Ĳ˵�(Ӧѡ�е�)
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from menu where id in (select menuId from rolemenu where role=?)";
		QueryRunner runner = new QueryRunner();
		Object[] params = { role };
		List<Menu> selectedMenus = runner.query(conn, sql, new BeanListHandler<Menu>(Menu.class), params);
		ConnectionUtils.releaseConnection(conn);
		// ѡ��
		for (Menu one : allMenus) {
			for (Menu selected : selectedMenus) {
				if (one.getId().equals(selected.getId())) {
					one.setChecked(true);
					break;
				}
			}
		}
		return allMenus;
	}

	/**
	 * �ύȨ��
	 */
	public void submitMenusByRole(String role, String[] menuIds) throws Exception {
		// ��ɾ����ɫ��Ӧ���в˵�
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from rolemenu where role =?";
		Object[] params = { role };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		// Ȼ����һ����rolemenu
		for (String menuId : menuIds) {
			RoleMenu one = new RoleMenu();
			one.setMenuId(menuId);
			one.setRole(role);
			this.insert(one);
		}
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void insert(RoleMenu roleMenu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into roleMenu(role,menuId)values(?,?)";
		Object[] params = { roleMenu.getRole(), roleMenu.getMenuId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
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
	 * ����
	 */
	public void update(RoleMenu roleMenu) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update roleMenu set roleId=?,menuId=? where id =?";
		Object[] params = { roleMenu.getRole(), roleMenu.getMenuId(), roleMenu.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
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
	 * ��ȡȫ��
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
	 * ��ȡ����
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
	 * ��ҳ��ѯ
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
