package cn.com.lemon.http.big;

public class TransferLog {
	public TransferLog() {
	}

	public static void sleep(int secong) {
		try {
			Thread.sleep(secong);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void log(String sMsg) {
		System.err.println(sMsg);
	}

	public static void log(int sMsg) {
		System.err.println(sMsg);
	}
}
