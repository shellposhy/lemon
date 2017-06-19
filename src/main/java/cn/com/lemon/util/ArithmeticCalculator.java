package cn.com.lemon.util;

import cn.com.lemon.common.exception.WrongExpressionException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticCalculator {
	private Stack<Object> st = new Stack<Object>();

	public double calculat(String exp) throws WrongExpressionException {
		checkExpression(exp);
		exp = initExpression(exp);

		String[] numbers = exp.split("[(\\(|\\))(\\+|\\-|\\*|\\/|\\^)(\\(|\\))]+");
		String[] signs = exp.split("(([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0))|([1-9]\\d*)");

		Double popResoult = null;
		int i = 0;
		int j = 1;

		popResoult = operate(Double.valueOf(numbers[(i + 0)]), signs[j], Double.valueOf(numbers[(i + 1)]),
				signs[(j + 1)]);
		while (j < signs.length - 2) {
			i++;
			j++;
			if (popResoult != null) {
				popResoult = operate(popResoult, signs[j], Double.valueOf(numbers[(i + 1)]), signs[(j + 1)]);
			} else {
				popResoult = operate(Double.valueOf(numbers[i]), signs[j], Double.valueOf(numbers[(i + 1)]),
						signs[(j + 1)]);
			}
		}
		if (this.st.size() > 0) {
			for (int n = 0; n < this.st.size(); n++) {
				popResoult = operate(popResoult, ")", Double.valueOf(0.0D), "+");
			}
		}
		return popResoult.doubleValue();
	}

	private Double operate(Double x, String o1, Double y, String o2) {
		CalculateUnit cu = new CalculateUnit();
		cu.setLeftValue(new Double(x.doubleValue()));

		char[] o1s = o1.toCharArray();
		char[] o2s = o2.toCharArray();
		int xLevel = getOSLevel(getOperateSign(o1));
		int yLevel = 0;
		int xKuoHao = 0;
		for (int i = 0; i < o1s.length; i++) {
			xKuoHao = isKuoHao(o1s[i]);
			if (xKuoHao == 1) {
				cu.setOpearteSign(getOperateSign(o1));
				push(cu);
				return null;
			}
			if (xKuoHao == -1) {
				if ((i == o1s.length - 1) || (isKuoHao(o1s[(i + 1)]) != 0)) {
					CalculateUnit popCu = pop();
					popCu.setRightValue(x);
					double rv = MathUtil.calculate(popCu.getLeftValue(), popCu.getOpearteSign(), popCu.getRightValue());
					x = new Double(rv);
					cu.setLeftValue(x);
				}
			} else if (i == o1s.length - 1) {
				if (isKuoHao(o2s[0]) == -1) {
					yLevel = xLevel;
				} else {
					yLevel = getOSLevel(getOperateSign(o2));
				}
				cu.setOpearteSign(getOperateSign(o1));
				if (xLevel < yLevel) {
					push(cu);
					return null;
				}
				cu.setRightValue(y);
				double rv = MathUtil.calculate(cu.getLeftValue(), cu.getOpearteSign(), cu.getRightValue());
				x = new Double(rv);
				return x;
			}
		}
		return x;
	}

	private void push(CalculateUnit cu) {
		this.st.push(cu);
	}

	private CalculateUnit pop() {
		CalculateUnit cu = (CalculateUnit) this.st.pop();
		return cu;
	}

	private static String getOperateSign(String s) {
		s = s.replaceAll("[\\(\\)]", "");
		return s;
	}

	private static int isKuoHao(char c) {
		switch (c) {
		case '(':
			return 1;
		case ')':
			return -1;
		}
		return 0;
	}

	private static int getOSLevel(String s) {
		if (("+".equals(s)) || ("-".equals(s))) {
			return 1;
		}
		if (("*".equals(s)) || ("/".equals(s))) {
			return 2;
		}
		if ("^".equals(s)) {
			return 3;
		}
		return 0;
	}

	private static String initExpression(String s) {
		if (s.startsWith("-")) {
			s = "0" + s;
		} else {
			s = "0+" + s;
		}
		s = s.replaceAll("\\(\\-", "(0-");
		int size = 0;
		do {
			size = s.length();
			s = s.replaceAll("\\(\\(", "(0+(");
		} while (s.length() != size);
		s = s + "+0+";
		return s;
	}

	private static void checkExpression(String s) throws WrongExpressionException {
		if ("".equals(s)) {
			throw new WrongExpressionException("表达式为空。");
		}
		Pattern p = Pattern.compile("[^\\+\\-\\*\\/\\^\\(\\)\\d.]+");
		Matcher m = p.matcher(s);
		if (m.find()) {
			throw new WrongExpressionException("表达式包含非法字符：\"" + m.group() + "\"");
		}
		p = Pattern.compile("[\\+\\-\\*\\/\\^]{2,}|\\)\\(|\\(\\)");
		m = p.matcher(s);
		if (m.find()) {
			throw new WrongExpressionException("表达式有错误：\"" + m.group() + "\"");
		}
		char[] c = s.toCharArray();
		int left_kuohao = 0;
		int right_kuohao = 0;
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '(') {
				left_kuohao++;
			} else if (c[i] == ')') {
				right_kuohao++;
			}
		}
		if (left_kuohao != right_kuohao) {
			throw new WrongExpressionException("左右括号不匹配。");
		}
	}
}
