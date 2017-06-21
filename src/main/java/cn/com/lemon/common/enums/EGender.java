package cn.com.lemon.common.enums;

/**
 * The gender enum type
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EGender {
	Man("男"), Woman("女"), Other("其他");
	private String value;

	private EGender(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
