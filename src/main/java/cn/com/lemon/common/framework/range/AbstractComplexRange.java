package cn.com.lemon.common.framework.range;

import java.util.Set;

public abstract class AbstractComplexRange<T> {
	private Set<AbstractSimpleRange<T>> positiveSet;
	private Set<AbstractSimpleRange<T>> minusSet;

	public Set<AbstractSimpleRange<T>> getPositiveSet() {
		return this.positiveSet;
	}

	public void setPositiveSet(Set<AbstractSimpleRange<T>> positiveSet) {
		this.positiveSet = positiveSet;
	}

	public Set<AbstractSimpleRange<T>> getMinusSet() {
		return this.minusSet;
	}

	public void setMinusSet(Set<AbstractSimpleRange<T>> minusSet) {
		this.minusSet = minusSet;
	}
}
