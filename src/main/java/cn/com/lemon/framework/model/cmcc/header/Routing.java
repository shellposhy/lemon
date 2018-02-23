package cn.com.lemon.framework.model.cmcc.header;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Routing information.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("Routing")
public class Routing implements Serializable{
	private static final long serialVersionUID = 1L;
	@XStreamAlias("HomeDomain")
	private String homeDomain;
	@XStreamAlias("RouteValue")
	private String routeValue;

	public String getHomeDomain() {
		return homeDomain;
	}

	public void setHomeDomain(String homeDomain) {
		this.homeDomain = homeDomain;
	}

	public String getRouteValue() {
		return routeValue;
	}

	public void setRouteValue(String routeValue) {
		this.routeValue = routeValue;
	}
}
