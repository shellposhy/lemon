package cn.com.lemon.framework.model;

import java.io.Serializable;

/**
 * Message base java bean.
 * 
 * @author shellpo shih
 * @version 1.0
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String header;
	private String content;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
