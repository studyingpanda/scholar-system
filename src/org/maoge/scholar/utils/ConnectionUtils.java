package org.maoge.scholar.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���ݿ����ӹ�����
 */
public class ConnectionUtils {

	// ��������Ĺ̶�����
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/scholar-system?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	private static String user = "root";
	private static String password = "Easy@0122";

	// ��ʼ����ʱ�����ȥ��Ū
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * ��ȡ����
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * �ͷ�����
	 */
	public static void releaseConnection(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
