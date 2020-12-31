package org.maoge.scholar.dao;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.maoge.scholar.model.Question;
import org.maoge.scholar.utils.ConnectionUtils;

/**
 * �������ݷ�����
 */
public class QuestionDao {
	/**
	 * ����
	 */
	public void insert(Question question) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "insert into question(userId,userName,departId,departName,content,reply)values(?,?,?,?,?,?)";
		Object[] params = { question.getUserId(), question.getUserName(), question.getDepartId(), question.getDepartName(), question.getContent(), question.getReply() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * �Ƴ�
	 */
	public void deleteById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "delete from question where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ����
	 */
	public void update(Question question) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "update question set userId=?,userName=?,departId=?,departName=?," + "content=?,reply=? where id =?";
		Object[] params = { question.getUserId(), question.getUserName(), question.getDepartId(), question.getDepartName(), question.getContent(), question.getReply(), question.getId() };
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, params);
		ConnectionUtils.releaseConnection(conn);
	}

	/**
	 * ��ȡһ��
	 */
	public Question getById(String id) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from question where id =?";
		Object[] params = { id };
		QueryRunner runner = new QueryRunner();
		Question question = (Question) runner.query(conn, sql, new BeanHandler<Question>(Question.class), params);
		ConnectionUtils.releaseConnection(conn);
		return question;
	}

	/**
	 * ��ȡȫ��
	 */
	public List<Question> getAll() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from question ";
		QueryRunner runner = new QueryRunner();
		List<Question> questions = runner.query(conn, sql, new BeanListHandler<Question>(Question.class));
		ConnectionUtils.releaseConnection(conn);
		return questions;
	}

	/**
	 * ��ȡ����
	 */
	public int getCount() throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from question ";
		QueryRunner runner = new QueryRunner();
		Number number = (Number) runner.query(conn, sql, new ScalarHandler());
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ
	 */
	public List<Question> getPage(int page, int rows) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from question limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { (page - 1) * rows };
		List<Question> questions = runner.query(conn, sql, new BeanListHandler<Question>(Question.class), params);
		ConnectionUtils.releaseConnection(conn);
		return questions;
	}

	/**
	 * ��ȡ����(ͨ��userId)
	 */
	public int getCount(String userId) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select count(id) from question where userId=?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { userId };
		Number number = (Number) runner.query(conn, sql, new ScalarHandler(), params);
		int value = number.intValue();
		ConnectionUtils.releaseConnection(conn);
		return value;
	}

	/**
	 * ��ҳ��ѯ(ͨ��userId)
	 */
	public List<Question> getPage(int page, int rows, String userId) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String sql = "select * from question where userId=? limit ?,?";
		QueryRunner runner = new QueryRunner();
		Object[] params = { userId,(page - 1) * rows, rows };
		List<Question> questions = runner.query(conn, sql, new BeanListHandler<Question>(Question.class), params);
		ConnectionUtils.releaseConnection(conn);
		return questions;
	}
}
