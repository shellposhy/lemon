package cn.com.lemon.util;

import cn.com.lemon.common.exception.WrongExpressionException;

public final class MathUtil {
	public static final String OPERATE_ADD = "+";
	public static final String OPERATE_SUBTRACT = "-";
	public static final String OPERATE_MULTIPLY = "*";
	public static final String OPERATE_DIVIDE = "/";
	public static final String OPERATE_POWER = "^";

	public static strictfp double calculate(double x, String op, double y) {
		double rv = 0.0D;
		if ("+".equals(op)) {
			rv = x + y;
		} else if ("-".equals(op)) {
			rv = x - y;
		} else if ("*".equals(op)) {
			rv = x * y;
		} else if ("/".equals(op)) {
			rv = x / y;
		} else if ("^".equals(op)) {
			rv = Math.pow(x, y);
		}
		return rv;
	}

	public static strictfp double calculate(Double x, String op, Double y) {
		return calculate(x.doubleValue(), op, y.doubleValue());
	}

	public static strictfp double calculate(String exp) throws WrongExpressionException {
		ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculator();
		return arithmeticCalculator.calculat(exp);
	}

	public static strictfp int getIntRandom(int m, int n) {
		return (int) getLongRandom(m, n);
	}

	public static strictfp long getLongRandom(long m, long n) {
		double p = m - 0.5D;
		double r = n + 0.5D;
		return Math.round(getDoubleRandom(p, r));
	}

	public static strictfp double getDoubleRandom(double p, double r) {
		return Math.random() * (r - p) + p;
	}

	public static strictfp double round(double d, int n) {
		double p = Math.pow(10.0D, n);
		d *= p;
		d = Math.round(d);
		d /= p;
		return d;
	}
}
