package cn.com.lemon.common.enums.cache;

/**
 * The cache data expiry policy
 * <p>
 * Set the date during type,When the time is expiry
 * 
 * @author shellpo shih
 * @version 1.0
 */
public enum EExpiryPolicy {
	Accessed("accessed"), Created("created"), Eternal("eternal"), Modified("updated"), Touched("touched");
	private String title;

	public static EExpiryPolicy valueof(int index) {
		if (index <= EExpiryPolicy.values().length - 1)
			return EExpiryPolicy.values()[index];
		else
			throw new ArrayIndexOutOfBoundsException();
	}

	private EExpiryPolicy(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

}
