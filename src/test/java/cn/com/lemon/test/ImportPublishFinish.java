package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.lemon.base.poi.Excels;
import cn.com.lemon.common.connection.Oracles;

public class ImportPublishFinish {

	public static void main(String[] args) throws SQLException, IOException {
		ImportPublishFinish.importData();

	}

	public static void importData() throws SQLException, IOException {
		Connection connection = Oracles.newInstance();
		// 输出文件
		File insertfile = new File("C:\\Users\\Administrator\\Desktop\\11.txt");
		if (!insertfile.exists())
			insertfile.createNewFile();
		FileWriter insertFileWriter = new FileWriter(insertfile);
		BufferedWriter insertWriter = new BufferedWriter(insertFileWriter);

		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\11.xlsx"), false);
		String sql = "INSERT INTO MY_PUBLISH_FINISH (PID) VALUES(?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		for (String[] data : datas) {
			if (!result.containsKey(Integer.valueOf(data[1])))
				result.put(Integer.valueOf(data[1]), Integer.valueOf(data[1]));
		}
		System.out.println("total=" + datas.size());
		System.out.println("size=" + result.size());
		for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
			ps.setInt(1, entry.getKey());
			ps.executeQuery();
		}

		insertWriter.close();
		insertFileWriter.close();
	}
}
