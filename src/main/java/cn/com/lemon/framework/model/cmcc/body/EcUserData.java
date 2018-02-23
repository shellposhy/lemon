package cn.com.lemon.framework.model.cmcc.body;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * User data information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("UserData")
public class EcUserData implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("MobNum")
	private String mobNum;
	@XStreamAlias("OprCode")
	private String oprCode;
	@XStreamAlias("UserPackage")
	private String userPackage;
	@XStreamAlias("ValidMonths")
	private String validMonths;

	public EcUserData() {
	}

	public EcUserData(String mobNum, String oprCode, String userPackage, String validMonths) {
		this.mobNum = mobNum;
		this.oprCode = oprCode;
		this.userPackage = userPackage;
		this.validMonths = validMonths;
	}

	public EcUserData(String mobNum, String userPackage) {
		this.mobNum = mobNum;
		this.userPackage = userPackage;
	}

	public String getMobNum() {
		return mobNum;
	}

	public void setMobNum(String mobNum) {
		this.mobNum = mobNum;
	}

	public String getOprCode() {
		return oprCode;
	}

	public void setOprCode(String oprCode) {
		this.oprCode = oprCode;
	}

	public String getUserPackage() {
		return userPackage;
	}

	public void setUserPackage(String userPackage) {
		this.userPackage = userPackage;
	}

	public String getValidMonths() {
		return validMonths;
	}

	public void setValidMonths(String validMonths) {
		this.validMonths = validMonths;
	}

}
