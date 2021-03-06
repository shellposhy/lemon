package cn.com.lemon.framework.model.cmcc.body;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import cn.com.lemon.base.xml.Xmls;

/**
 * EC User data information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("UserInfo")
public class EcUserInfoBody implements Serializable {
	private static final long serialVersionUID = 1L;
	@XStreamAlias("ProductID")
	private String productID;
	@XStreamImplicit(itemFieldName = "UserData")
	private List<EcUserData> list;
	@XStreamAlias("EffRule")
	private String effRule;

	/**
	 * change {@code EcUserInfoBody} to XML {@code String}
	 * 
	 * @param data
	 *            {@code EcUserInfoBody} the data
	 * @param isUseCDATA
	 *            is use CDATA
	 * @param isContainHeader
	 *            is contail xml header
	 * @param clazz
	 * @return {@link String} the xml data
	 */
	public static String generator(EcUserInfoBody data, boolean isUseCDATA, boolean isContainHeader) {
		return Xmls.generator(data, isUseCDATA, isContainHeader, EcUserInfoBody.class);
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public List<EcUserData> getList() {
		return list;
	}

	public void setList(List<EcUserData> list) {
		this.list = list;
	}

	public String getEffRule() {
		return effRule;
	}

	public void setEffRule(String effRule) {
		this.effRule = effRule;
	}

}
