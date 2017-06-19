package cn.com.lemon.common.framework.judge;

import java.util.LinkedList;
import java.util.List;

public class JudgeResult<T> {
	private boolean passed;
	List<RuleResult<T>> ruleResults;

	public JudgeResult() {
		this.ruleResults = new LinkedList<RuleResult<T>>();
	}

	public boolean isPassed() {
		return this.passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public List<RuleResult<T>> getRuleResults() {
		return this.ruleResults;
	}

	public void addRuleResult(RuleResult<T> rr) {
		this.ruleResults.add(rr);
	}
}
