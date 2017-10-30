package cn.com.lemon.test;

import static cn.com.lemon.util.RegularUtil.matchNumeric;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.com.lemon.base.Strings;
import cn.com.lemon.common.mysql.Connections;

public class SynData2DBTest {

	public static void main(String[] args) throws IOException, SQLException {
		SynData2DBTest.process("C:\\Users\\Administrator\\Desktop\\plzy-xz.csv");
	}

	public static void process(String filename) throws IOException, SQLException {
		// data file
		File file = new File(filename);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		// conncection
		Connection connection = Connections.newInstance();
		String s;
		while ((s = raf.readLine()) != null) {
			String content = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String[] contents = content.split(",");
			int size = contents.length;
			if (size > 3) {
				if (matchNumeric(contents[0])) {
					save(connection, contents, "plxz");
				}
			}
		}
		raf.close();
		connection.close();
	}

	public static void save(Connection connection, String[] contents, String dbname)
			throws SQLException, UnsupportedEncodingException {
		PreparedStatement ps = connection
				.prepareStatement("insert into " + dbname + "(AAR001,AAR009,AAR002,AAA110) values(?,?,?,?)");
		ps.setString(1, contents[1]);
		ps.setString(2, new String(contents[2].trim().toString().getBytes("UTF-8")));
		if (Strings.isNullOrEmpty(contents[3])) {
			ps.setString(3, null);
		} else {
			ps.setString(3, contents[3]);
		}
		ps.setString(4, "1");
		ps.executeUpdate();
	}
}
