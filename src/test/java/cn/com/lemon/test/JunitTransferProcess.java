package cn.com.lemon.test;

import cn.com.lemon.http.big.Transfer;
import cn.com.lemon.http.big.download.TransferProcess;

public class JunitTransferProcess {

	public JunitTransferProcess() {
		Transfer transfer = new Transfer("http://101.201.196.204:8080/pic/upic/4/2017/3/29821490440366365.jpg",
				"d:/test", "22.jpg", 5, 0);
		TransferProcess process = new TransferProcess(transfer);
		process.start();
	}

	public static void main(String[] args) {
		new JunitTransferProcess();
	}
}
