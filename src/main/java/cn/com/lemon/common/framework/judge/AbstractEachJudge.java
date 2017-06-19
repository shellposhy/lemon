package cn.com.lemon.common.framework.judge;

import java.util.List;

public abstract class AbstractEachJudge<P, T> implements IJudge<P, T> {
	public JudgeResult<T> doJudge(P target, List<IRule<P, T>> exRuleList) {
		List<IRule<P, T>> ruleList = getRuleList();
		if (exRuleList != null) {
			ruleList.addAll(exRuleList);
		}
		RuleResult<T> rr = null;
		JudgeResult<T> jr = new JudgeResult<T>();
		jr.setPassed(true);
		for (IRule<P, T> r : ruleList) {
			rr = r.doCheck(target);
			if (!rr.isPassed()) {
				jr.setPassed(false);
			}
			jr.addRuleResult(rr);
		}
		return jr;
	}

	public JudgeResult<T> doJudge(P target) {
		return doJudge(target, null);
	}

	public abstract List<IRule<P, T>> getRuleList();
}
