package cn.com.lemon.common.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Static utility methods pertaining to {@code Connections} primitives.
 * <p>
 * The base utility contain basic operate by {@code newInstance} and
 * {@code close}
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Mysqls {

	private Mysqls() {
	}

	public static final String url = "jdbc:mysql://192.168.2.88:3306/tas?useUnicode=true&amp;characterEncoding=UTF-8";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "test";
	public static final String password = "1234";

	private static ThreadLocal<Connection> connectthreadLocal = new ThreadLocal<Connection>();

	/**
	 * Create the {@code Connection}
	 * 
	 * @param url
	 * @param name
	 * @param user
	 * @param password
	 * @return
	 * @return {@code Connection} the mysql connection
	 */
	public static Connection newInstance(String url, String name, String user, String password) {
		Connection connect = connectthreadLocal.get();
		if (connect == null) {
			try {
				Class.forName(name);
				connect = DriverManager.getConnection(url, user, password);
				connectthreadLocal.set(connect);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return connect;
	}

	/**
	 * Create the {@code Connection}
	 * 
	 * @return {@code Connection} the mysql connection
	 */
	public static Connection newInstance() {
		Connection connect = connectthreadLocal.get();
		if (connect == null) {
			try {
				Class.forName(name);
				connect = DriverManager.getConnection(url, user, password);
				connectthreadLocal.set(connect);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return connect;
	}

	/**
	 * Close the database {@code Connection}
	 * 
	 * @return {@code Boolean}
	 */
	public static boolean close(Connection conn) {
		try {
			if (!conn.isClosed()) {
				conn.close();
				return true;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
