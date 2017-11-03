package cn.com.lemon.test.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import cn.com.lemon.common.mysql.Connections;
import cn.com.lemon.test.process.model.SynData;

/**
 * 区域划分数据处理
 * 
 */
public final class DistrictProcess {

	private DistrictProcess() {

	}

	public static void main(String[] args) throws SQLException, IOException {
		DistrictProcess.process("C:/Users/Administrator/Desktop/new.csv", "C:/Users/Administrator/Desktop/fa15.sql");
	}

	public static void process(String dataFileName, String output) throws SQLException, IOException {
		Map<String, SynData> data = data();
		// 输出文件
		File outputfile = new File(output);
		if (!outputfile.exists())
			outputfile.createNewFile();
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		// 变更记录
		File recordFile = new File("C:/Users/Administrator/Desktop/fa22.sql");
		if (!recordFile.exists())
			recordFile.createNewFile();
		FileWriter recordFileWriter = new FileWriter(recordFile);
		BufferedWriter recordWriter = new BufferedWriter(recordFileWriter);
		// 新数据文件
		File file = new File(dataFileName);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		String s;
		int id = 661732;
		while ((s = raf.readLine()) != null) {
			String content = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String[] contents = content.split(",");
			int size = contents.length;
			SynData synData = data.get(contents[0]);
			if (size == 3) {
				if (null != synData) {// 历史数据
					if (synData.getAAR002().trim().equals(contents[2])
							&& synData.getAAR009().trim().equals(contents[1].trim())) {
						writer.write("insert into  fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values("
								+ synData.getAAA011() + ",'" + synData.getAAR001() + "','" + synData.getAAR009() + "','"
								+ synData.getAAR002() + "','1');");
						writer.newLine();
					} else {
						writer.write("insert into  fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values("
								+ synData.getAAA011() + ",'" + synData.getAAR001() + "','" + synData.getAAR009() + "','"
								+ synData.getAAR002() + "','0');");
						writer.newLine();
						if (!contents[2].equals("N")) {
							writer.write("insert into  fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + id + ",'"
									+ contents[0] + "','" + contents[1] + "','" + contents[2] + "','1');");
							writer.newLine();
							writer.newLine();
							id++;
						}
					}
				} else {// 新数据
					writer.write("insert into fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + id + ",'"
							+ contents[0] + "','" + contents[1] + "','" + contents[2] + "','1');");
					writer.newLine();
					id++;
				}
			} else if (size == 6) {
				if (null != synData) {
					if (contents[2].equals("N")) {// 转移或变更
						writer.write("insert into  fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values("
								+ synData.getAAA011() + ",'" + synData.getAAR001() + "','" + synData.getAAR009() + "','"
								+ synData.getAAR002() + "','0');");
						writer.newLine();
						recordWriter.write("insert into fa22(AAA011,AAR001,AAR009,AAR002,AAA110,AAA111,AAA112) values("
								+ synData.getAAA011() + ",'" + synData.getAAR001() + "','" + synData.getAAR009() + "','"
								+ synData.getAAR002() + "','0','" + contents[3] + "','" + contents[5] + "');");
						recordWriter.newLine();
					} else {
						writer.write(
								"insert into  fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + synData.getAAA011()
										+ ",'" + contents[0] + "','" + contents[1] + "','" + contents[2] + "','1');");
						writer.newLine();
					}
				} else {// 新数据
					writer.write("insert into fa15(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + id + ",'"
							+ contents[0] + "','" + contents[1] + "','" + contents[2] + "','1');");
					writer.newLine();
					id++;
				}
			}
		}
		writer.close();
		fileWriter.close();
		recordWriter.close();
		recordFileWriter.close();
		raf.close();
	}

	/**
	 * 组装原始数据
	 * 
	 * @return {@link Map}
	 */
	public static Map<String, SynData> data() throws SQLException {
		Map<String, SynData> result = new TreeMap<String, SynData>();
		Connection connection = Connections.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM fa11");
		if (resultSet.next()) {
			while (resultSet.next()) {
				SynData data = new SynData();
				String key = resultSet.getString("AAR001");
				data.setAAA011(resultSet.getInt("AAA011"));
				data.setAAR001(key);
				data.setAAR009(resultSet.getString("AAR009"));
				data.setAAR002(resultSet.getString("AAR002"));
				data.setAAA110(resultSet.getString("AAA110"));
				result.put(key, data);
			}
		}
		resultSet.close();
		statement.close();
		connection.close();
		return result;
	}
}
