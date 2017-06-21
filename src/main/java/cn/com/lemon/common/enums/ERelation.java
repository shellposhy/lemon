package cn.com.lemon.common.enums;

/**
 * The operate relation enum type
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum ERelation {
	Equal("="), UnEqual("<>"), LessThan("<"), MoreThan(">"), LessThanOrEqual("<="), MoreThanOrEqual(">="), Like(
			"like"), And("and"), Or("or"), Not("not"), NotIn("not in");
	private String value;

	private ERelation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
