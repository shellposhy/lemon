package cn.com.lemon.framework.model.cmcc.body;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * DOM fail information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("FailInfo")
public class DomFailInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamAlias("MobNum")
	private String mobNum;
	@XStreamAlias("Rsp")
	private String rsp;
	@XStreamAlias("rspDesc")
	private String rspDesc;

	public DomFailInfo() {
	}

	public DomFailInfo(String mobNum, String rsp, String rspDesc) {
		this.mobNum = mobNum;
		this.rsp = rsp;
		this.rspDesc = rspDesc;
	}

	public String getMobNum() {
		return mobNum;
	}

	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}

	public String getRsp() {
		return rsp;
	}

	public void setRsp(String rsp) {
		this.rsp = rsp;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}
}
