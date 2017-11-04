package cn.com.lemon.test.process.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.com.lemon.common.connection.Oracles;
import cn.com.lemon.test.process.model.SynData;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

public class UserProcess {

	// 区划父节点
	private final static String PARENT = "100";

	public static void main(String[] args) throws SQLException, IOException {
		UserProcess.process("C:/Users/Administrator/Desktop/change.csv");
	}

	public static void process(String output) throws SQLException, IOException {
		// 输出文件
		File outputfile = new File(output);
		if (!outputfile.exists())
			outputfile.createNewFile();
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		Connection connection = Oracles.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement
				.executeQuery("select AFR042,AFR001,AZC001,AZC002,AZC003,AZC004,AZC005 from FR02");
		while (resultSet.next()) {
			String content = resultSet.getString("AFR042") + "," + resultSet.getString("AFR001");
			if (!isNullOrEmpty(resultSet.getString("AZC005"))) {// 所属村
				if (check(connection, statement, resultSet.getString("AZC005"))) {
					content += "," + resultSet.getString("AZC001") + "," + resultSet.getString("AZC002") + ","
							+ resultSet.getString("AZC003") + "," + resultSet.getString("AZC004") + ","
							+ resultSet.getString("AZC005");
					writer.write(content);
					writer.newLine();
					System.out.println(content);
				}
			} else {
				if (!isNullOrEmpty(resultSet.getString("AZC004"))) {
					if (check(connection, statement, resultSet.getString("AZC004"))) {
						content += "," + resultSet.getString("AZC001") + "," + resultSet.getString("AZC002") + ","
								+ resultSet.getString("AZC003") + "," + resultSet.getString("AZC004");
						writer.write(content);
						writer.newLine();
						System.out.println(content);
					}
				} else {
					if (!isNullOrEmpty(resultSet.getString("AZC003"))) {
						if (check(connection, statement, resultSet.getString("AZC003"))) {
							content += "," + resultSet.getString("AZC001") + "," + resultSet.getString("AZC002") + ","
									+ resultSet.getString("AZC003");
							writer.write(content);
							writer.newLine();
							System.out.println(content);
						}
					} else {
						if (!isNullOrEmpty(resultSet.getString("AZC002"))) {
							if (check(connection, statement, resultSet.getString("AZC002"))) {
								content += "," + resultSet.getString("AZC001") + "," + resultSet.getString("AZC002");
								writer.write(content);
								writer.newLine();
								System.out.println(content);
							} else {
								if (check(connection, statement, resultSet.getString("AZC001"))) {
									content += "," + resultSet.getString("AZC001");
									writer.write(content);
									writer.newLine();
									System.out.println(content);
								}
							}
						}
					}
				}
			}
		}
		writer.close();
		fileWriter.close();
		resultSet.close();
		// statement.close();
		// connection.close();
	}

	public static boolean check(Connection connection, Statement statement, String code) throws SQLException {
		List<SynData> datas = district(connection, statement, code);
		if (datas != null && datas.size() > 0) {
			if (datas.size() > 1) {
				for (SynData data : datas) {
					if (data.getAAA110().equals("0")) {
						return true;
					}
				}
			} else {
				if (!isNullOrEmpty(change(connection, statement, code))) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<SynData> district(Connection connection, Statement statement, String code) throws SQLException {
		List<SynData> list = null;
		ResultSet resultSet = statement.executeQuery("select * from FA11 where AAR001='" + code + "'");
		if (resultSet.next()) {
			list = new ArrayList<SynData>();
			while (resultSet.next()) {
				SynData data = new SynData();
				String key = resultSet.getString("AAR001");
				data.setAAA011(resultSet.getInt("AAA011"));
				data.setAAR001(key);
				data.setAAR009(resultSet.getString("AAR009"));
				data.setAAR002(resultSet.getString("AAR002"));
				data.setAAA110(resultSet.getString("AAA110"));
				list.add(data);
			}
		}
		return list;
	}

	public static String change(Connection connection, Statement statement, String code) throws SQLException {
		ResultSet resultSet = statement.executeQuery("select * from FA11_CHANGE_LOG where AAR001='" + code + "'");
		if (resultSet.next()) {
			while (resultSet.next()) {
				return resultSet.getString("AAA112");
			}
		}
		return null;
	}

	public static Map<String, String> replace(Connection connection, Statement statement, String code)
			throws SQLException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		recurve(connection, statement, map, code);
		return map;
	}

	private static void recurve(Connection connection, Statement statement, Map<String, String> result, String code)
			throws SQLException {
		List<SynData> datas = district(connection, statement, code);
		if (datas != null && datas.size() > 0) {
			SynData data = null;
			if (datas.size() > 1) {
				for (SynData synData : datas) {
					if (synData.getAAA110().equals("1")) {
						data = synData;
						break;
					}
				}
			} else {
				data = datas.get(0);
			}
			if (data != null) {
				result.put(data.getAAR001(), data.getAAR009());
				String parent = data.getAAR002();
				if (parent != null && parent.trim().length() > 0) {
					if (Integer.valueOf(parent).intValue() > Integer.valueOf(PARENT).intValue()) {
						recurve(connection, statement, result, parent);
					}
				}
			}
		}
	}
}
