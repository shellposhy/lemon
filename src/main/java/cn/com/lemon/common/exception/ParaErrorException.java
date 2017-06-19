package cn.com.lemon.common.exception;

public class ParaErrorException extends Exception {
	private static final long serialVersionUID = 1L;

	public ParaErrorException(Object object) {
		super("参数错误。" + object);
	}
}
