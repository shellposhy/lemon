package cn.com.lemon.framework.model.cmcc.header;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Transaction flow information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("TransInfo")
public class TransInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("SessionID")
	private String sessionID;
	@XStreamAlias("TransIDO")
	private String transIDO;
	@XStreamAlias("TransIDOTime")
	private String transIDOTime;
	@XStreamAlias("TransIDH")
	private String transIDH;
	@XStreamAlias("TransIDHTime")
	private String transIDHTime;

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getTransIDO() {
		return transIDO;
	}

	public void setTransIDO(String transIDO) {
		this.transIDO = transIDO;
	}

	public String getTransIDOTime() {
		return transIDOTime;
	}

	public void setTransIDOTime(String transIDOTime) {
		this.transIDOTime = transIDOTime;
	}

	public String getTransIDH() {
		return transIDH;
	}

	public void setTransIDH(String transIDH) {
		this.transIDH = transIDH;
	}

	public String getTransIDHTime() {
		return transIDHTime;
	}

	public void setTransIDHTime(String transIDHTime) {
		this.transIDHTime = transIDHTime;
	}

}
