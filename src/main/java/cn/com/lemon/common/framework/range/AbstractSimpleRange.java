package cn.com.lemon.common.framework.range;

public abstract class AbstractSimpleRange<T> {
	private T start;
	private T end;

	public T getStart() {
		return this.start;
	}

	public void setStart(T start) {
		this.start = start;
	}

	public T getEnd() {
		return this.end;
	}

	public void setEnd(T end) {
		this.end = end;
	}

	public boolean isSingle() {
		if (this.start.equals(this.end)) {
			return true;
		}
		return false;
	}

	public abstract boolean isInRange(T paramT);
}