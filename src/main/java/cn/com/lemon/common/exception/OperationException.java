package cn.com.lemon.common.exception;

public class OperationException extends Exception {
	private Object object;
	private static final long serialVersionUID = 1L;

	public OperationException(String msg) {
		super(msg);
	}

	public OperationException(String msg, Object object) {
		super(msg);
		this.object = object;
	}

	public OperationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public OperationException(String msg, Object object, Throwable cause) {
		super(msg, cause);
		this.object = object;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
