package cn.com.lemon.test;

import java.io.File;
import java.util.List;

import cn.com.lemon.base.Strings;
import cn.com.lemon.base.poi.Excels;

public class ExcelDataProcess {

	public static void main(String[] args) {
		 ExcelDataProcess.process();
		//System.out.println(ContentEncryptUtil.encrypt("ContentEncryptUtil", "shfpback"));
		//System.out.println(ContentEncryptUtil.encrypt("105025", "shfpback"));
	}

	public static void process() {
		List<String[]> qiu = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\qi.xlsx"), true);
		List<String[]> shi = Excels.read(new File("C:\\Users\\Administrator\\Desktop\\sh.xlsx"), true);

		int count = 0;

		for (String[] data : qiu) {
			String key = data[0];
			String value = data[1];

			String tKey = "";
			String tValue = "";
			for (String[] test : shi) {
				tKey = test[0];
				tValue = test[1];
				if (key.equals(tKey)) {
					break;
				}
			}

			if (Strings.isNullOrEmpty(tKey) || !value.equals(tValue)) {
				count++;
				System.out.println(key);
			}
		}

		System.out.println(count);
	}
}
