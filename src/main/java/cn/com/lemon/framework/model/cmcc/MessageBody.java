package cn.com.lemon.framework.model.cmcc;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Message body.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("InterBOSS")
public class MessageBody implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("SvcCont")
	private String svcCont;

	public String getSvcCont() {
		return svcCont;
	}

	public void setSvcCont(String svcCont) {
		this.svcCont = svcCont;
	}

}
