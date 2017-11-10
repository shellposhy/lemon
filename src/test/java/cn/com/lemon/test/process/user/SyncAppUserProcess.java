package cn.com.lemon.test.process.user;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;

public class SyncAppUserProcess {

	public static void main(String[] args) throws SQLException, IOException {
		SyncAppUserProcess.process("C:/Users/Administrator/Desktop/s11.csv");
	}

	public static void process(String output) throws SQLException, IOException {
		// 输出文件
		File outputfile = new File(output);
		if (!outputfile.exists())
			outputfile.createNewFile();
		FileWriter fileWriter = new FileWriter(output);
		BufferedWriter writer = new BufferedWriter(fileWriter);

		File file = new File("C:/Users/Administrator/Desktop/s.csv");
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		String s;
		while ((s = raf.readLine()) != null) {
			String content = new String(s.getBytes("ISO-8859-1"), "utf-8");
			String[] contents = content.split(",");
			int size = contents.length;
			if (size == 11) {
				writer.write("update shfpdjpt.app_users set regionid='" + contents[1] + "," + contents[2] + ","
						+ contents[3] + "," + contents[4] + "," + contents[5] + "',address='" + contents[6] + ","
						+ contents[7] + "," + contents[8] + "," + contents[9] + "," + contents[10] + "' where ID="
						+ contents[0] + ";");
				writer.newLine();
			}
		}
		raf.close();
		writer.close();
		fileWriter.close();
	}
}
