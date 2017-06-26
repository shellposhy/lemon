package cn.com.lemon.http;

public class Monitoring {
	public Monitoring() {
	}

	public static void sleep(int second) {
		try {
			Thread.sleep(second);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void log(String msg) {
		System.err.println(msg);
	}

	public static void log(int msg) {
		System.err.println(msg);
	}
}
