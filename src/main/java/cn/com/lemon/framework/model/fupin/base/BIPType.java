package cn.com.lemon.framework.model.fupin.base;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Transaction type information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("BIPType")
public class BIPType implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("BIPCode")
	private String bipCode;
	@XStreamAlias("ActivityCode")
	private String activityCode;
	@XStreamAlias("ActionCode")
	private String actionCode;

	public String getBipCode() {
		return bipCode;
	}

	public void setBipCode(String bipCode) {
		this.bipCode = bipCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
}
