package cn.com.lemon.test.process.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.com.lemon.common.connection.Oracles;
import cn.com.lemon.test.process.model.SynUser;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

public class SynDatabaseProcess {

	private static final String INSERT_SQL_PATH = "C:/Users/Administrator/Desktop/FR02_CHANGE_LOG_INSERT.sql";
	private static final String UPDATE_SQL_PATH = "C:/Users/Administrator/Desktop/FR02_UPDATE.sql";
	private static final String DATA_FILE_PATH = "C:/Users/Administrator/Desktop/change_new.csv";

	public static void main(String[] args) throws IOException, SQLException {
		SynDatabaseProcess.process(DATA_FILE_PATH);
	}

	public static void process(String dataFile) throws IOException, SQLException {
		// 输出文件
		File insertfile = new File(INSERT_SQL_PATH);
		if (!insertfile.exists())
			insertfile.createNewFile();
		FileWriter insertFileWriter = new FileWriter(insertfile);
		BufferedWriter insertWriter = new BufferedWriter(insertFileWriter);

		File updateFile = new File(UPDATE_SQL_PATH);
		FileWriter updateFileWriter = new FileWriter(updateFile);
		BufferedWriter updateWriter = new BufferedWriter(updateFileWriter);
		// 数据文件
		File file = new File(dataFile);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		Map<String, SynUser> data = list();
		String s;
		while ((s = raf.readLine()) != null) {
			String content = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String[] contents = content.split(",");
			int size = contents.length;
			if (size == 9) {
				if (data.containsKey(contents[0])) {
					String[] codes = combine(contents[7]);
					String[] names = combine(contents[8]);
					if (codes.length == 5) {
						updateWriter.write("update FR02 set AZC001='" + codes[4] + "',AZC002='" + codes[3]
								+ "',AZC003='" + codes[2] + "',AZC004='" + codes[1] + "',AZC005='" + codes[0]
								+ "',AZC007='" + codes[4] + "',AZC008='" + codes[3] + "',AZC009='" + codes[2]
								+ "',AZC010='" + codes[1] + "',AZC011='" + codes[0] + "',AFR003='" + codes[4] + ","
								+ codes[3] + "," + codes[2] + "," + codes[1] + "," + codes[0] + "',AFR036='" + names[4]
								+ "," + names[3] + "," + names[2] + "," + names[1] + "," + names[0] + "' where AFR042="
								+ contents[0] + ";");
						updateWriter.newLine();
						insertWriter.write("insert into FR02_CHANGE_LOG  values('" + data.get(contents[0]).getAFR042()
								+ "','" + data.get(contents[0]).getAFR001() + "','" + data.get(contents[0]).getAZC001()
								+ "','" + data.get(contents[0]).getAZC002() + "','" + data.get(contents[0]).getAZC003()
								+ "','" + data.get(contents[0]).getAZC004() + "','" + data.get(contents[0]).getAZC005()
								+ "');");
						insertWriter.newLine();
					} else if (codes.length == 4) {
						updateWriter.write("update FR02 set AZC001='" + codes[3] + "',AZC002='" + codes[2]
								+ "',AZC003='" + codes[1] + "',AZC004='" + codes[0] + "',AZC007='" + codes[3]
								+ "',AZC008='" + codes[2] + "',AZC009='" + codes[1] + "',AZC010='" + codes[0]
								+ "',AFR003='" + codes[3] + "," + codes[2] + "," + codes[1] + "," + codes[0]
								+ "',AFR036='" + "," + names[3] + "," + names[2] + "," + names[1] + "," + names[0]
								+ "' where AFR042=" + contents[0] + ";");
						updateWriter.newLine();
						insertWriter.write("insert into FR02_CHANGE_LOG values('" + data.get(contents[0]).getAFR042()
								+ "','" + data.get(contents[0]).getAFR001() + "','" + data.get(contents[0]).getAZC001()
								+ "','" + data.get(contents[0]).getAZC002() + "','" + data.get(contents[0]).getAZC003()
								+ "','" + data.get(contents[0]).getAZC004() + "','" + data.get(contents[0]).getAZC005()
								+ "');");
						insertWriter.newLine();
					} else if (codes.length == 3) {
						updateWriter.write("update FR02 set AZC001='" + codes[2] + "',AZC002='" + codes[1]
								+ "',AZC003='" + codes[0] + "',AZC007='" + codes[2] + "',AZC008='" + codes[1]
								+ "',AZC009='" + codes[0] + "',AFR003='" + codes[2] + "," + codes[1] + "," + codes[0]
								+ "',AFR036='" + names[2] + "," + names[1] + "," + names[0] + "' where AFR042="
								+ contents[0] + ";");
						updateWriter.newLine();
						insertWriter.write("insert into FR02_CHANGE_LOG  values('" + data.get(contents[0]).getAFR042()
								+ "','" + data.get(contents[0]).getAFR001() + "','" + data.get(contents[0]).getAZC001()
								+ "','" + data.get(contents[0]).getAZC002() + "','" + data.get(contents[0]).getAZC003()
								+ "','" + data.get(contents[0]).getAZC004() + "','" + data.get(contents[0]).getAZC005()
								+ "');");
						insertWriter.newLine();
					} else if (codes.length == 2) {
						updateWriter.write("update FR02 set AZC001='" + codes[1] + "',AZC002='" + codes[0]
								+ "',AZC007='" + codes[1] + "',AZC008='" + codes[0] + "',AFR003='" + codes[1] + ","
								+ codes[0] + "',AFR036='" + names[1] + "," + names[0] + "' where AFR042=" + contents[0]
								+ ";");
						updateWriter.newLine();
						insertWriter.write("insert into FR02_CHANGE_LOG values('" + data.get(contents[0]).getAFR042()
								+ "','" + data.get(contents[0]).getAFR001() + "','" + data.get(contents[0]).getAZC001()
								+ "','" + data.get(contents[0]).getAZC002() + "','" + data.get(contents[0]).getAZC003()
								+ "','" + data.get(contents[0]).getAZC004() + "','" + data.get(contents[0]).getAZC005()
								+ "');");
						insertWriter.newLine();
					} else if (codes.length == 1) {
						updateWriter
								.write("update FR02 set AZC001='" + codes[0] + "',AZC007='" + codes[0] + "',AFR003='"
										+ codes[0] + "',AFR036='" + names[0] + "' where AFR042=" + contents[0] + ";");
						updateWriter.newLine();
						insertWriter.write("insert into FR02_CHANGE_LOG  values('" + data.get(contents[0]).getAFR042()
								+ "','" + data.get(contents[0]).getAFR001() + "','" + data.get(contents[0]).getAZC001()
								+ "','" + data.get(contents[0]).getAZC002() + "','" + data.get(contents[0]).getAZC003()
								+ "','" + data.get(contents[0]).getAZC004() + "','" + data.get(contents[0]).getAZC005()
								+ "');");
						insertWriter.newLine();
					}
				}

			}
		}
		raf.close();
		insertWriter.close();
		insertFileWriter.close();
		updateWriter.close();
		updateFileWriter.close();
	}

	public static String[] combine(String conetnt) {
		if (!isNullOrEmpty(conetnt)) {
			String[] result = conetnt.split("N");
			return result;
		}
		return null;
	}

	public static Map<String, SynUser> list() throws SQLException {
		Map<String, SynUser> result = null;
		Connection connection = Oracles.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(
				"select AFR042,AFR001,AZC001,AZC002,AZC003,AZC004,AZC005,AZC007,AZC008,AZC009,AZC010,AZC011 from FR02 where  AZC005 like '4311%'");
		if (resultSet.next()) {
			result = new LinkedHashMap<String, SynUser>();
			while (resultSet.next()) {
				Integer id = resultSet.getInt("AFR042");
				SynUser user = new SynUser();
				user.setAFR042(id);
				user.setAFR001(isNullOrEmpty(resultSet.getString("AFR001")) ? "" : resultSet.getString("AFR001"));
				user.setAZC001(isNullOrEmpty(resultSet.getString("AZC001")) ? "" : resultSet.getString("AZC001"));
				user.setAZC002(isNullOrEmpty(resultSet.getString("AZC002")) ? "" : resultSet.getString("AZC002"));
				user.setAZC003(isNullOrEmpty(resultSet.getString("AZC003")) ? "" : resultSet.getString("AZC003"));
				user.setAZC004(isNullOrEmpty(resultSet.getString("AZC004")) ? "" : resultSet.getString("AZC004"));
				user.setAZC005(isNullOrEmpty(resultSet.getString("AZC005")) ? "" : resultSet.getString("AZC005"));
				user.setAZC007(isNullOrEmpty(resultSet.getString("AZC007")) ? "" : resultSet.getString("AZC007"));
				user.setAZC008(isNullOrEmpty(resultSet.getString("AZC008")) ? "" : resultSet.getString("AZC008"));
				user.setAZC009(isNullOrEmpty(resultSet.getString("AZC009")) ? "" : resultSet.getString("AZC009"));
				user.setAZC0010(isNullOrEmpty(resultSet.getString("AZC010")) ? "" : resultSet.getString("AZC010"));
				user.setAZC0011(isNullOrEmpty(resultSet.getString("AZC011")) ? "" : resultSet.getString("AZC011"));
				result.put(id.toString(), user);
			}
		}
		resultSet.close();
		statement.close();
		connection.close();
		return result;
	}
}
