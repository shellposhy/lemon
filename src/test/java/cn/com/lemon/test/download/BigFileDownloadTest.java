package cn.com.lemon.test.download;

import cn.com.lemon.http.Transfer;
import cn.com.lemon.http.download.DownloadProcess;

public class BigFileDownloadTest {

	public BigFileDownloadTest() {
		Transfer transfer = new Transfer("http://speed.myzone.cn/WindowsXP_SP2.exe", "d:/test", "WindowsXP_SP2.exe", 10,
				0);
		DownloadProcess process = new DownloadProcess(transfer);
		process.start();
	}

	public static void main(String[] args) {
		new BigFileDownloadTest();
	}
}
