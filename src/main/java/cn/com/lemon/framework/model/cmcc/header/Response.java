package cn.com.lemon.framework.model.cmcc.header;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Response node.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("Response")
public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("RspType")
	private String rspType;
	@XStreamAlias("RspCode")
	private String rspCode;
	@XStreamAlias("RspDesc")
	private String rspDesc;

	public String getRspType() {
		return rspType;
	}

	public void setRspType(String rspType) {
		this.rspType = rspType;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}

}
