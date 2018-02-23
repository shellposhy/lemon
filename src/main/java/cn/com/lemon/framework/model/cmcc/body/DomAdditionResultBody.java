package cn.com.lemon.framework.model.cmcc.body;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import cn.com.lemon.base.xml.Xmls;

/**
 * DOM User result .
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("AdditionResult")
public class DomAdditionResultBody implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("TransIDO")
	private String transIDO;
	@XStreamAlias("Status")
	private String status;
	@XStreamAlias("OperSeq")
	private String operSeq;
	@XStreamAlias("SuccNum")
	private int succNum;
	@XStreamImplicit(itemFieldName = "SuccInfo")
	private List<DomSuccInfo> succList;
	@XStreamAlias("FailNum")
	private int failNum;
	@XStreamImplicit(itemFieldName = "FailInfo")
	private List<DomFailInfo> failList;

	/**
	 * change {@code DomUserResultBody} to XML {@code String}
	 * 
	 * @param data
	 *            {@code DomUserResultBody} the data
	 * @param isUseCDATA
	 *            is use CDATA
	 * @param isContainHeader
	 *            is contail xml header
	 * @param clazz
	 * @return {@link String} the xml data
	 */
	public static String generator(DomAdditionResultBody data, boolean isUseCDATA, boolean isContainHeader) {
		return Xmls.generator(data, isUseCDATA, isContainHeader, DomAdditionResultBody.class);
	}

	public String getTransIDO() {
		return transIDO;
	}

	public void setTransIDO(String transIDO) {
		this.transIDO = transIDO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperSeq() {
		return operSeq;
	}

	public void setOperSeq(String operSeq) {
		this.operSeq = operSeq;
	}

	public int getSuccNum() {
		return succNum;
	}

	public void setSuccNum(int succNum) {
		this.succNum = succNum;
	}

	public List<DomSuccInfo> getSuccList() {
		return succList;
	}

	public void setSuccList(List<DomSuccInfo> succList) {
		this.succList = succList;
	}

	public int getFailNum() {
		return failNum;
	}

	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}

	public List<DomFailInfo> getFailList() {
		return failList;
	}

	public void setFailList(List<DomFailInfo> failList) {
		this.failList = failList;
	}

}
