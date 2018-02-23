package cn.com.lemon.framework.model.cmcc.body;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * DOM success information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("SuccInfo")
public class DomSuccInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("SuccTel")
	private String succTel;
	@XStreamAlias("EffDate")
	private String effDate;

	public DomSuccInfo() {
	}

	public DomSuccInfo(String succTel) {
		this.succTel = succTel;
	}

	public DomSuccInfo(String succTel, String effDate) {
		this.succTel = succTel;
		this.effDate = effDate;
	}

	public String getSuccTel() {
		return succTel;
	}

	public void setSuccTel(String succTel) {
		this.succTel = succTel;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
}
