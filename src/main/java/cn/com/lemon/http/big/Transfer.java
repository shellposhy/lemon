package cn.com.lemon.http.big;

/**
 * The <code>Transfer</code> class is the basic bean to describe file info when
 * be transferred.
 * 
 * @author shellpo shih
 * @version 1.0
 * 
 */
public class Transfer {
	private String url;
	private String filePath;
	private String fileName;
	private int segment;
	private int percentage;

	// default value of segment is 5
	public Transfer() {
		this("", "", "", 5, 0);
	}

	public Transfer(String url, String filePath, String fileName, int segment, int percentage) {
		this.url = url;
		this.filePath = filePath;
		this.fileName = fileName;
		this.segment = segment;
		this.percentage = percentage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSegment() {
		return segment;
	}

	public void setSegment(int segment) {
		this.segment = segment;
	}

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

}
