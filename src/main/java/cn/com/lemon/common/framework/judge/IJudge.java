package cn.com.lemon.common.framework.judge;

import java.util.List;

public abstract interface IJudge<P, T> {
	public abstract JudgeResult<T> doJudge(P paramP);

	public abstract JudgeResult<T> doJudge(P paramP, List<IRule<P, T>> paramList);

	public abstract List<IRule<P, T>> getRuleList();
}
