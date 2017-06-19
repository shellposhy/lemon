package cn.com.lemon.common.exception;

public class ParaTypeErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ParaTypeErrorException(Object object) {
		super("参数类型错误。" + object);
	}
}
