package cn.com.lemon.common.enums.date;

/**
 * The month enum type
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EMonth {
	All(0), Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sept(9), Oct(10), Nov(11), Dec(12);
	private int value;

	private EMonth(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
