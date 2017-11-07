package cn.com.lemon.test.process.user;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.com.lemon.common.connection.Oracles;
import cn.com.lemon.test.process.model.SynData;

public class SyncProcess {

	public static void main(String[] args) throws SQLException, IOException, InterruptedException {
		SyncProcess.process("C:/Users/Administrator/Desktop/change_new.csv");
	}

	public static void process(String output) throws SQLException, IOException {
		// 输出文件
		File outputfile = new File(output);
		if (!outputfile.exists())
			outputfile.createNewFile();
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		// 数据文件
		Connection connection = Oracles.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(
				"select AFR042,AFR001,AZC001,AZC002,AZC003,AZC004,AZC005 from FR02 where  AZC005 like '4311%'");
		// 区划数据化
		List<SynData> districtList = district();
		List<SynData> changeList = change();
		int total = 1;
		int count = 1;
		long start = System.currentTimeMillis() / 1000;
		// 写入初始数据
		writer.write("编号,姓名,区域类别,原区划编号,类型,原区划编号,新区划编号,新区划,新区划名称");
		writer.newLine();
		while (resultSet.next()) {
			total++;
			String content = resultSet.getString("AFR042") + "," + resultSet.getString("AFR001");
			if (!isNullOrEmpty(resultSet.getString("AZC005"))) {// 所属街道
				content += ",5," + resultSet.getString("AZC005");
				if (check(content, writer, resultSet.getString("AZC005"), districtList, changeList)) {
					count++;
				}
			} else {
				if (!isNullOrEmpty(resultSet.getString("AZC004"))) {// 所属乡镇
					content += ",4," + resultSet.getString("AZC004");
					if (check(content, writer, resultSet.getString("AZC004"), districtList, changeList)) {
						count++;
					}
				} else {
					if (!isNullOrEmpty(resultSet.getString("AZC003"))) {// 所属区县
						content += ",3," + resultSet.getString("AZC003");
						if (check(content, writer, resultSet.getString("AZC003"), districtList, changeList)) {
							count++;
						}
					} else {
						if (!isNullOrEmpty(resultSet.getString("AZC002"))) {// 所属市
							content += ",2," + resultSet.getString("AZC002");
							if (check(content, writer, resultSet.getString("AZC002"), districtList, changeList)) {
								count++;
							} else {
								if (!isNullOrEmpty(resultSet.getString("AZC001"))) {// 所属省
									content += ",1," + resultSet.getString("AZC001");
									if (check(content, writer, resultSet.getString("AZC001"), districtList,
											changeList)) {
										count++;
									}
								}
							}
						}
					}
				}
			}
		}
		long end = System.currentTimeMillis() / 1000;
		// 统计结果
		long time = end - start;
		writer.write("总贫困户数,区划变更数,执行时间");
		writer.newLine();
		writer.write(total + "," + count + "," + time);
		// 关闭连接
		writer.close();
		fileWriter.close();
		resultSet.close();
		statement.close();
		connection.close();
	}

	/**
	 * 检查
	 */
	public static boolean check(String content, BufferedWriter writer, String code, List<SynData> district,
			List<SynData> change) throws IOException {
		boolean result = false;
		// 变更区域表
		if (change != null && change.size() > 0) {
			for (SynData data : change) {
				if (data.getAAR001().trim().equals(code.trim())) {
					result = true;
					recurve(content + ",change," + code + "," + data.getAAA112(), "", "", data.getAAA112(), district,
							writer);
					break;
				}
			}
		}
		// 重复数据情况
		if (!result) {
			List<SynData> tmp = list(code, district);
			// 数据处理
			if (tmp != null && tmp.size() > 0) {
				if (tmp.size() > 1) {
					for (SynData data : tmp) {
						if (data.getAAA110().equals("1")) {
							result = true;
							recurve(content + ",new," + code + "," + data.getAAR001(), "", "", data.getAAR001(),
									district, writer);
							break;
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 最新区划列表
	 * 
	 * @return {@link List}
	 */
	public static List<SynData> district() throws SQLException {
		List<SynData> result = null;
		Connection connection = Oracles.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from FA11");
		if (resultSet.next()) {
			result = new ArrayList<SynData>();
			while (resultSet.next()) {
				SynData data = new SynData();
				String key = resultSet.getString("AAR001");
				data.setAAA011(resultSet.getInt("AAA011"));
				data.setAAR001(key);
				data.setAAR009(resultSet.getString("AAR009"));
				data.setAAR002(resultSet.getString("AAR002"));
				data.setAAA110(resultSet.getString("AAA110"));
				result.add(data);
			}
		}
		return result;
	}

	/**
	 * 区划变更记录
	 * 
	 * @return {@link List}
	 */
	public static List<SynData> change() throws SQLException {
		List<SynData> result = null;
		Connection connection = Oracles.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from FA11_CHANGE_LOG");
		if (resultSet.next()) {
			result = new ArrayList<SynData>();
			while (resultSet.next()) {
				SynData data = new SynData();
				data.setAAR001(resultSet.getString("AAR001"));
				data.setAAA112(resultSet.getString("AAA112"));
				result.add(data);
			}
		}
		return result;
	}

	/**
	 * 根据编码查询列表
	 * 
	 * @param code
	 * @param districts
	 * @return {@link List}
	 */
	public static List<SynData> list(String code, List<SynData> district) {
		List<SynData> tmp = new ArrayList<SynData>();
		for (SynData data : district) {
			if (data.getAAR001().trim().equals(code.trim())) {
				tmp.add(data);
			}
		}
		return tmp;
	}

	/**
	 * 递归
	 * 
	 * @param content
	 * @param code
	 * @return {@link String}
	 * @throws IOException
	 */
	public static void recurve(String content, String key, String name, String code, List<SynData> district,
			BufferedWriter writer) throws IOException {
		List<SynData> tmp = list(code, district);
		SynData synData = null;
		if (null != tmp && tmp.size() > 0) {
			if (tmp.size() == 1) {
				key += tmp.get(0).getAAR001() + "N";
				name += tmp.get(0).getAAR009() + "N";
				synData = tmp.get(0);
			} else {
				for (SynData data : tmp) {
					if (data.getAAA110().equals("1")) {
						key += data.getAAR001() + "N";
						name += data.getAAR009() + "N";
						synData = data;
						break;
					}
				}
			}
		}
		if (synData != null) {
			if (!isNullOrEmpty(synData.getAAR002())) {
				if (!synData.getAAR002().equals("0") && !synData.getAAR002().equals("100")
						&& !synData.getAAR002().equals("-1")) {
					recurve(content, key, name, synData.getAAR002(), district, writer);
				} else {
					if (key.length() > 0) {
						key = key.substring(0, key.length() - 1);
						name = name.substring(0, name.length() - 1);
						System.out.println(content + "," + key + "," + name);
						writer.write(content + "," + key + "," + name);
						writer.newLine();
					}
				}
			}
		}
	}

}
