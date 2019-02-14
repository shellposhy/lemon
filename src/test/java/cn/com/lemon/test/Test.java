package cn.com.lemon.test;

public class Test {

	public static void main(String[] args) {
		System.out.println("儿子患肿瘤：妻子放弃，我一定要坚持下去！".length());

		System.out.println("522632017000".substring(6, 9));

		String test = "/content";
		System.out.println(test.startsWith("/") ? test.substring(1) : test);
		
		String aa="zgshfpWEB201811211124774721";
		System.out.println(aa.indexOf("WEB") != -1);
	}
}
