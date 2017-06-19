package cn.com.lemon.util;

class CalculateUnit {
	private Double leftValue;
	private Double rightValue;
	private String opearteSign;

	public void setLeftValue(Double lv) {
		this.leftValue = lv;
	}

	public Double getLeftValue() {
		return this.leftValue;
	}

	public void setRightValue(Double rv) {
		this.rightValue = rv;
	}

	public Double getRightValue() {
		return this.rightValue;
	}

	public void setOpearteSign(String os) {
		this.opearteSign = os;
	}

	public String getOpearteSign() {
		return this.opearteSign;
	}

	public String toString() {
		return this.leftValue + " " + this.opearteSign + " " + this.rightValue;
	}
}
