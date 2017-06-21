package cn.com.lemon.common.enums.date;

/**
 * The quarter enum type
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EQuarter {
	All(0), First(1), Second(2), Third(3), Fourth(4);
	private int value;

	private EQuarter(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
