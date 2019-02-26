package cn.com.lemon.test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Test {

	public static void main(String[] args) {
		System.out.println(BigDecimal.class.getName());
		
		BigInteger decimal=new BigInteger("12202223");
		System.out.println(decimal.getClass().getName());
		System.out.println(decimal.toString());
		System.out.println(decimal.longValue());
	}
}
