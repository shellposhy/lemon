package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import cn.com.lemon.base.DateUtil;
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

		List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\33.xlsx"), true);
		Connection connection = Oracles.newInstance();
		// String sql = " update shfpdjpt.my_publish_tmp set withdrawbatch=?
		// where id=?";
		StringBuffer sb = new StringBuffer(500);
		sb.append("INSERT INTO shfpdjpt.PAY_CHECK_FORM_TMP(ID,PAY_CHECK_NUM,MONEY_TOTAL,CHECK_NUM,USER_ID,");
		sb.append("USER_NAME,STATUS,PAY_TYPE,CHECK_TYPE,WITHDRAW_TYPE,UPLOAD_TIME,UPLOAD_PATH,");
		sb.append("SUCCESS_NUM,FAIL_NUM,CREATE_TIME,CREATE_ID,CREATE_NAME)");
		sb.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement ps = connection.prepareStatement(sb.toString());
		System.out.println(datas.size());
		// data[0]:需求编号
		// data[1]:打款批次
		// data[2]:打款日期
		// data[3]:到账日期
		int count = 43;
		for (String[] data : datas) {
			ps.setInt(1, count);// ID
			ps.setString(2, data[0]);// PAY_CHECK_NUM
			ps.setInt(3, Integer.valueOf(data[2]).intValue());// MONEY_TOTAL
			ps.setInt(4, Integer.valueOf(data[1]));// CHECK_NUM
			ps.setInt(5, 483074);// USER_ID
			ps.setString(6, "王稳");// USER_NAME
			ps.setInt(7, 1);// STATUS
			ps.setInt(8, 4);// PAY_TYPE
			ps.setInt(9, 1);// CHECK_TYPE
			ps.setInt(10, 0);// WITHDRAW_TYPE
			java.util.Date date = DateUtil.parse(data[0].split("-")[1], "yyyyMMdd");
			ps.setDate(11, new Date(date.getTime()));// UPLOAD_TIME
			ps.setString(12, data[0].replaceAll("-", "") + "_0.jpg");// UPLOAD_PATH
			ps.setInt(13, Integer.valueOf(data[1]));// SUCCESS_NUM
			ps.setInt(14, 0);// FAIL_NUM
			ps.setDate(15, new Date(date.getTime()));// CREATE_TIME
			ps.setInt(16, 483074);// CREATE_ID
			ps.setString(17, "王稳");// CREATE_NAME
			ps.executeUpdate();
			count++;
		}

		insertWriter.flush();
		insertWriter.close();
		insertFileWriter.close();
	}
}
