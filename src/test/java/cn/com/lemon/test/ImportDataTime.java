package cn.com.lemon.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.csvreader.CsvReader;

import cn.com.lemon.common.connection.Oracles;

public class ImportDataTime {
	public static void main(String[] args) throws SQLException, IOException {
		ImportDataTime.process();
	}

	public static void process() throws SQLException, IOException {

		File insertfile = new File("C:\\Users\\Administrator\\Desktop\\22.sql");
		if (!insertfile.exists())
			insertfile.createNewFile();
		FileWriter insertFileWriter = new FileWriter(insertfile);
		BufferedWriter insertWriter = new BufferedWriter(insertFileWriter);

		//List<String[]> datas = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\33.xlsx"), true);

		CsvReader csvReader = new CsvReader("C:\\Users\\Administrator\\Desktop\\11.csv");
		csvReader.readHeaders();

		Connection connection = Oracles.newInstance();
		String sql = " update shfpdjpt.my_publish_tmp set withdrawbatch=? where id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		// System.out.println(datas.size());
		// data[0]:需求编号
		// data[1]:打款批次
		// data[2]:打款日期
		// data[3]:到账日期
		int count = 0;

		while (csvReader.readRecord()) {
			count++;
			int getMoney = Integer.valueOf(csvReader.get("GETMONEY")).intValue();
			int sumMoney = Integer.valueOf(csvReader.get("MONEYNUM")).intValue();
			int dayNum = Integer.valueOf(csvReader.get("DAYSNUM")).intValue();
			int type = 0;// 0帮扶满额1提前提现2时长到期
			if (sumMoney > 0) {
				if (sumMoney >= getMoney) {
					type = 0;
				} else {
					if (dayNum < 90) {
						type = 1;
					} else {
						type = 2;
					}
				}
				
				//System.out.println(csvReader.get("ID"));
				insertWriter.write(
						"update my_publish t set t.paymoneytype=" + type + " where id=" + csvReader.get("ID") + ";");
				insertWriter.newLine();
			}
		}
		
		ps.execute();
		// for (String[] data : datas) {
		// // ps.setInt(1, count);// ID
		// if (!Strings.isNullOrEmpty(data[0])) {
		// insertWriter.write(
		// "insert into
		// shfpdjpt.publish_feedback(id,publish_id,feedback_type,check_status,create_by,create_date,update_by,update_date)");
		// insertWriter.write(
		// " VALUES(SEQ_PUBLISH_FEEDBACK.nextval," + data[0] +
		// ",0,0,483074,sysdate,483074,sysdate);");
		// insertWriter.newLine();
		// }
		//
		// count++;
		// }

		System.out.println(count);

		insertWriter.flush();
		insertWriter.close();
		insertFileWriter.close();
	}
}
