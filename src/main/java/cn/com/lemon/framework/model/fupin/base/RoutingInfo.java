package cn.com.lemon.framework.model.fupin.base;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Mobile business routing objects.
 * 
 * @author shellpo shih
 * @version 1.0
 */
@XStreamAlias("RoutingInfo")
public class RoutingInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	@XStreamAlias("OrigDomain")
	private String origDomain;
	@XStreamAlias("RouteType")
	private String routeType;
	@XStreamAlias("Routing")
	private Routing routing;

	public String getOrigDomain() {
		return origDomain;
	}

	public void setOrigDomain(String origDomain) {
		this.origDomain = origDomain;
	}

	public String getRouteType() {
		return routeType;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public Routing getRouting() {
		return routing;
	}

	public void setRouting(Routing routing) {
		this.routing = routing;
	}
}
