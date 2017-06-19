package cn.com.lemon.common.exception;

public class WrongExpressionException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongExpressionException() {
	}

	public WrongExpressionException(String str) {
		super("错误的表达式\n" + str);
	}
}
