package cn.com.lemon.common.framework.judge;

public abstract interface IRule<P, T> {
	public abstract RuleResult<T> doCheck(P paramP);

	public abstract String getDescription();
}
