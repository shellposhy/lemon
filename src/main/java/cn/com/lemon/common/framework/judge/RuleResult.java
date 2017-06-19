package cn.com.lemon.common.framework.judge;

import cn.com.lemon.common.framework.EErrors;

public class RuleResult<T> {
	private boolean passed;
	private String msg;
	private EErrors error;
	private T target;

	public boolean isPassed() {
		return this.passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public EErrors getError() {
		return this.error;
	}

	public void setError(EErrors error) {
		this.error = error;
	}

	public T getTarget() {
		return this.target;
	}

	public void setTarget(T target) {
		this.target = target;
	}
}
