package cn.com.lemon.framework.model.cmcc;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cn.com.lemon.framework.model.cmcc.header.BIPType;
import cn.com.lemon.framework.model.cmcc.header.Response;
import cn.com.lemon.framework.model.cmcc.header.RoutingInfo;
import cn.com.lemon.framework.model.cmcc.header.SNReserve;
import cn.com.lemon.framework.model.cmcc.header.TransInfo;

/**
 * Message header.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("InterBOSS")
public class MessageHeader implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("Version")
	private String version;
	@XStreamAlias("TestFlag")
	private String testFlag;
	@XStreamAlias("BIPType")
	private BIPType bipType;
	@XStreamAlias("RoutingInfo")
	private RoutingInfo routingInfo;
	@XStreamAlias("TransInfo")
	private TransInfo transInfo;
	@XStreamAlias("SNReserve")
	private SNReserve snReserve;
	@XStreamAlias("Response")
	private Response response;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(String testFlag) {
		this.testFlag = testFlag;
	}

	public BIPType getBipType() {
		return bipType;
	}

	public void setBipType(BIPType bipType) {
		this.bipType = bipType;
	}

	public RoutingInfo getRoutingInfo() {
		return routingInfo;
	}

	public void setRoutingInfo(RoutingInfo routingInfo) {
		this.routingInfo = routingInfo;
	}

	public TransInfo getTransInfo() {
		return transInfo;
	}

	public void setTransInfo(TransInfo transInfo) {
		this.transInfo = transInfo;
	}

	public SNReserve getSnReserve() {
		return snReserve;
	}

	public void setSnReserve(SNReserve snReserve) {
		this.snReserve = snReserve;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
