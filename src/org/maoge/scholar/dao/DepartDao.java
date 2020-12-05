package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Depart;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * �������ݷ�����
 */
public class DepartDao {
	/**
	 * ����
	 */
	public void insert(Depart depart) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into depart(name,type,parentId)values(?,?,?)";
		Object[] params = { depart.getName(), depart.getType(), depart.getParentId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from depart where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void update(Depart depart) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update depart set name=?,type=?,parentId=? where id =?";
		Object[] params = { depart.getName(), depart.getType(), depart.getParentId(), depart.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
	 */
	public Depart getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from depart where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		Depart depart = (Depart) runner.query(conn, sql, new BeanHandler<Depart>(Depart.class), params);
		ConnectionUtils.releaseConnection(conn);
		return depart;
	}

	/**
	 * ��ȡȫ��
	 */
	public List<Depart> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from depart ";
		QueryRunner runner = new QueryRunner();
		List<Depart> departs = runner.query(conn, sql, new BeanListHandler<Depart>(Depart.class));
		ConnectionUtils.releaseConnection(conn);
		return departs;
	}

	/**
	 * ��ȡ����
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from depart ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ(����)
	 */
	public List<Depart> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select son.*,parent.name as parentName from depart son left join depart parent on son.parentId=parent.id limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows, rows };
		List<Depart> departs = runner.query(conn, sql, new BeanListHandler<Depart>(Depart.class), params);
		ConnectionUtils.releaseConnection(conn);
		return departs;
	}
}
