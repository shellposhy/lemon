package cn.com.lemon.test.process.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import cn.com.lemon.common.mysql.Connections;

public class SyncJob implements Runnable {
	private int num;
	volatile int  count = 1;

	public SyncJob(int num) {
		this.num = num;
	}

	public void run() {
		Connection connection = Connections.newInstance();
		int start = (num - 1) * 120000;
		int size = 120000;
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM fa13   LIMIT " + start + "," + size);
			while (rs.next()) {
				String code = rs.getString("AAR001");
				Connection conn = Connections.newInstance();
				Statement st = conn.createStatement();
				ResultSet old = st.executeQuery("SELECT * FROM fa11 WHERE AAR001='" + code + "'");
				if (old.next()) {
					int id = old.getInt("AAA011");
					PreparedStatement pstmt = connection.prepareStatement("UPDATE fa13 SET AAA011=? WHERE AAR001=?");
					pstmt.setInt(1, id);
					pstmt.setString(2, code);
					pstmt.executeUpdate();
					System.out.println("Thread=" + num + ",count=" + count + ",id=" + id + ",code=" + code);
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getNum() {
		return num;
	}

}
