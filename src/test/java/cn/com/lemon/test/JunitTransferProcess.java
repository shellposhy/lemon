package cn.com.lemon.test;

import cn.com.lemon.http.big.Transfer;
import cn.com.lemon.http.big.download.TransferProcess;

public class JunitTransferProcess {

	public JunitTransferProcess() {
		Transfer transfer = new Transfer("http://a.gdown.baidu.com/data/wisegame/dcdafeb31f9b2034/xunlei_10906.apk",
				"d:/test", "22.apk", 10, 0);
		TransferProcess process = new TransferProcess(transfer);
		process.start();
	}

	public static void main(String[] args) {
		new JunitTransferProcess();
	}
}
