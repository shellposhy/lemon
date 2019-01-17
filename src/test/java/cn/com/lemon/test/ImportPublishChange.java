package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import cn.com.lemon.base.poi.Excels;
import cn.com.lemon.common.connection.Oracles;

public class ImportPublishChange {

	public static void main(String[] args) throws SQLException, IOException {
		ImportPublishChange.process();
		// System.out.println("AXBF-20170607-10000047".replaceAll("-", ""));
	}

	public static void process() throws SQLException, IOException {

		File insertfile = new File("C:\\Users\\Administrator\\Desktop\\22.sql");
		if (!insertfile.exists())
			insertfile.createNewFile();
		FileWriter insertFileWriter = new FileWriter(insertfile);
		BufferedWriter insertWriter = new BufferedWriter(insertFileWriter);

		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\11.xlsx"), true);
		Connection connection = Oracles.newInstance();
		// String sql = " update shfpdjpt.my_publish_tmp set withdrawbatch=?
		// where id=?";
		StringBuffer sb = new StringBuffer(500);
		sb.append("INSERT INTO shfpdjpt.my_publish_pay_record(ID,CARDNUM,FAIL_REASON)");
		sb.append(" VALUES(?,?,?)");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		System.out.println(datas.size());
		// data[0]:需求编号
		// data[1]:打款批次
		// data[2]:打款日期
		// data[3]:到账日期
		int count = 1;
		for (String[] data : datas) {
			ps.setInt(1, Integer.valueOf(data[0]));// ID
			ps.setString(2, data[1]);
			ps.setString(3, data[2].trim());
			ps.executeUpdate();
			count++;
		}
		System.out.println(count);
		insertWriter.flush();
		insertWriter.close();
		insertFileWriter.close();
	}
}
