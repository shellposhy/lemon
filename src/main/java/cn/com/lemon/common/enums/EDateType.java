package cn.com.lemon.common.enums;

/**
 * The date type enum type
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EDateType {
	Common("yyyy-MM-dd"), Simple("yyyyMMdd"), Chinese("yyyy年MM月dd日");
	private String title;

	private EDateType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
