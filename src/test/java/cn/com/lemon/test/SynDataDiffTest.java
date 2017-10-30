package cn.com.lemon.test;

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

import cn.com.lemon.common.mysql.Connections;

public class SynDataDiffTest {

	public static void main(String[] args) throws SQLException, IOException {
		SynDataDiffTest.syscListData("C:\\Users\\Administrator\\Desktop\\diff.sql");
	}

	public static void syscListData(String outputname) throws SQLException, IOException {
		// 历史数据
		List<SynData> newData = new ArrayList<SynData>();
		Connection newConn = Connections.newInstance();
		Statement newState = newConn.createStatement();
		ResultSet newRs = newState.executeQuery("SELECT * FROM fa11");
		while (newRs.next()) {
			SynData data = new SynData();
			data.setAAA011(newRs.getInt("AAA011"));
			data.setAAR001(newRs.getString("AAR001"));
			data.setAAR009(newRs.getString("AAR009"));
			data.setAAR002(newRs.getString("AAR002"));
			data.setAAA110(newRs.getString("AAA110"));
			newData.add(data);
		}

		// 新数据
		Connection oldConn = Connections.newInstance();
		Statement oldState = oldConn.createStatement();
		ResultSet oldRs = oldState.executeQuery("SELECT * FROM fa14");
		Map<String, Integer> oldData = new LinkedHashMap<String, Integer>();
		while (oldRs.next()) {
			oldData.put(oldRs.getString("AAR001"), oldRs.getInt("AAA011"));
		}

		// 数据同步
		File output = new File(outputname);
		if (!output.exists()) {
			output.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		if (oldData.size() > 0 && newData.size() > 0) {
			for (SynData data : newData) {
				String key = data.getAAR001();
				System.out.println("key=" + key);
				try {
					oldData.get(key);
				} catch (Exception e) {
					System.out.println("insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + data.getAAA011()
							+ ",'" + key + "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
					writer.write("insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + data.getAAA011()
							+ ",'" + key + "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
					writer.newLine();
				}
			}
		}
		writer.flush();
		writer.close();
		fileWriter.close();
		oldConn.close();
		newConn.close();
	}
}
