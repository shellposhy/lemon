package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.com.lemon.base.Strings;
import cn.com.lemon.common.mysql.Connections;

import static cn.com.lemon.util.RegularUtil.matchNumeric;

public class DataSynchieseTest {

	public static void process(String filename, String outputname) throws IOException, SQLException {
		// output
		File output = new File(outputname);
		if (!output.exists()) {
			output.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);
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
				if (matchNumeric(contents[0])&&content.contains("批量转移新增")) {
					//save(connection, contents, "plzy");
					
				}else{
					writer.write(content);
					writer.newLine();
				}
			}
		}
		writer.flush();
		writer.close();
		fileWriter.close();
		raf.close();
		connection.close();
	}

	public static void syscListData(String outputname) throws SQLException, IOException {
		// 新数据
		List<SynData> newData = new ArrayList<SynData>();
		Connection newConn = Connections.newInstance();
		Statement newState = newConn.createStatement();
		ResultSet newRs = newState.executeQuery("SELECT * FROM fa13");
		while (newRs.next()) {
			SynData data = new SynData();
			data.setAAA011(newRs.getInt("AAA011"));
			data.setAAR001(newRs.getString("AAR001"));
			data.setAAR009(newRs.getString("AAR009"));
			data.setAAR002(newRs.getString("AAR002"));
			data.setAAA110(newRs.getString("AAA110"));
			newData.add(data);
		}

		// 历史数据
		Connection oldConn = Connections.newInstance();
		Statement oldState = oldConn.createStatement();
		ResultSet oldRs = oldState.executeQuery("SELECT * FROM fa11");
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
		int count = 661731;
		if (oldData.size() > 0 && newData.size() > 0) {
			for (SynData data : newData) {
				String key = data.getAAR001();
				try {
					int value = oldData.get(key);
					if (value > 0) {
						System.out.println("insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + value + ",'"
								+ key + "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
						writer.write("insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + value + ",'" + key
								+ "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
						writer.newLine();
					}
				} catch (Exception e) {
					count++;
					System.out.println("======insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + count
							+ ",'" + key + "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
					writer.write("insert into fa14(AAA011,AAR001,AAR009,AAR002,AAA110) values(" + count + ",'" + key
							+ "','" + data.getAAR009() + "','" + data.getAAR002() + "','1');");
					writer.newLine();
				}
			}
		}
		writer.flush();
		writer.close();
		fileWriter.close();
	}

	public static void main(String[] args) throws IOException, SQLException {
		// DataSynchieseTest.syscListData("C:\\Users\\Administrator\\Desktop\\data1.sql");
		// DataSynchieseTest.process("C:\\Users\\Administrator\\Desktop\\data.csv",
		// "C:\\Users\\Administrator\\Desktop\\data_1.csv");
		DataSynchieseTest.process("C:\\Users\\Administrator\\Desktop\\plzy.csv",
				"C:\\Users\\Administrator\\Desktop\\plzy-zy.csv");
	}

	public static void sync() {
		// DataSynchieseTest.sync();
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 5; i++) {
			SyncJob job = new SyncJob(i);
			executor.execute(job);
		}
		executor.shutdown();
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
