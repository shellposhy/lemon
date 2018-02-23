package cn.com.lemon.framework.model.cmcc.header;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * SN reserve information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("SNReserve")
public class SNReserve implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("TransIDC")
	private String transIDC;
	@XStreamAlias("ConvID")
	private String convID;
	@XStreamAlias("CutOffDay")
	private String cutOffDay;
	@XStreamAlias("OSNTime")
	private String osnTime;
	@XStreamAlias("OSNDUNS")
	private String osnduns;
	@XStreamAlias("HSNDUNS")
	private String hsnduns;
	@XStreamAlias("MsgSender")
	private String msgSender;
	@XStreamAlias("MsgReceiver")
	private String msgReceiver;
	@XStreamAlias("Priority")
	private String priority;
	@XStreamAlias("ServiceLevel")
	private String serviceLevel;
	@XStreamAlias("SvcContType")
	private String svcContType;

	public String getTransIDC() {
		return transIDC;
	}

	public void setTransIDC(String transIDC) {
		this.transIDC = transIDC;
	}

	public String getConvID() {
		return convID;
	}

	public void setConvID(String convID) {
		this.convID = convID;
	}

	public String getCutOffDay() {
		return cutOffDay;
	}

	public void setCutOffDay(String cutOffDay) {
		this.cutOffDay = cutOffDay;
	}

	public String getOsnTime() {
		return osnTime;
	}

	public void setOsnTime(String osnTime) {
		this.osnTime = osnTime;
	}

	public String getOsnduns() {
		return osnduns;
	}

	public void setOsnduns(String osnduns) {
		this.osnduns = osnduns;
	}

	public String getHsnduns() {
		return hsnduns;
	}

	public void setHsnduns(String hsnduns) {
		this.hsnduns = hsnduns;
	}

	public String getMsgSender() {
		return msgSender;
	}

	public void setMsgSender(String msgSender) {
		this.msgSender = msgSender;
	}

	public String getMsgReceiver() {
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver) {
		this.msgReceiver = msgReceiver;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getSvcContType() {
		return svcContType;
	}

	public void setSvcContType(String svcContType) {
		this.svcContType = svcContType;
	}
}
