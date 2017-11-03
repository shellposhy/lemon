package cn.com.lemon.test.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import cn.com.lemon.common.mysql.Connections;
import cn.com.lemon.test.process.model.SynData;

public class DistrictExtraProcess {

	public static void main(String[] args) throws SQLException, IOException {
		DistrictExtraProcess.process("C:/Users/Administrator/Desktop/fa16_extra.csv");
	}

	public static void process(String output) throws SQLException, IOException {
		// 输出文件
		File outputfile = new File(output);
		if (!outputfile.exists())
			outputfile.createNewFile();
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		// 数据
		Map<String, SynData> dataList = data();
		if (dataList.size() > 0) {
			for (Map.Entry<String, SynData> data : dataList.entrySet()) {
				writer.write(data.getValue().getAAA011() + "," + data.getValue().getAAR001() + ","
						+ data.getValue().getAAR009());
				writer.newLine();
			}
		}
		writer.close();
		fileWriter.close();
	}

	public static Map<String, SynData> data() throws SQLException {
		Map<String, SynData> result = new TreeMap<String, SynData>();
		Connection connection = Connections.newInstance();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM fa16");
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
