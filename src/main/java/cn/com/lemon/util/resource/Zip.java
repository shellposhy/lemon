package cn.com.lemon.util.resource;

/**
 * The zip bean properties
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class Zip {
	private long zipFileSize;
	private long contentSize;

	public long getZipFileSize() {
		return zipFileSize;
	}

	public void setZipFileSize(long zipFileSize) {
		this.zipFileSize = zipFileSize;
	}

	public long getContentSize() {
		return contentSize;
	}

	public void setContentSize(long contentSize) {
		this.contentSize = contentSize;
	}
}