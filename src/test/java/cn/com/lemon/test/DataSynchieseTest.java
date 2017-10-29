package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.com.lemon.base.Strings;
import cn.com.lemon.common.mysql.Connections;
import cn.com.lemon.util.RegularUtil;

public class DataSynchieseTest {
	public static void main(String[] args) throws IOException, SQLException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\plzy.csv");
		File newFile = new File("C:\\Users\\Administrator\\Desktop\\new1test.csv");
		if (!newFile.exists()) {
			newFile.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(newFile);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		Connection con = Connections.newInstance();
		PreparedStatement ps = con.prepareStatement("insert into pl12 values(?,?,?)");
		String s;
		while ((s = raf.readLine()) != null) {
			String content = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String[] contents = content.split(",");
			int size = contents.length;
			if (size >= 4) {
				if (!Strings.isNullOrEmpty(contents[0]) && !RegularUtil.matchLetter(contents[0])) {
					String test = toString(contents[1]);
					if (test != null) {
						System.out.println(contents[1] + "==" + test);
						ps.setString(1, contents[1]);
						ps.setString(2, new String(contents[2].trim().toString().getBytes("UTF-8")));
						if (Strings.isNullOrEmpty(contents[3])) {
							ps.setString(3, null);
						} else {
							ps.setString(3, contents[3]);
						}
						ps.executeUpdate();
					}
				}
			}
		}
		writer.flush();
		writer.close();
		fileWriter.close();
		raf.close();

	}

	public static String toString(String s) {
		try {
			BigDecimal bd = new BigDecimal(s);
			return bd.toPlainString();
		} catch (NumberFormatException e) {
			return null;
		}

	}
}
