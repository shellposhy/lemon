package cn.com.lemon.common.framework.range;

import java.net.Inet4Address;

import cn.com.lemon.util.NetUtil;

public class IPv4SimpleRange extends AbstractSimpleRange<Inet4Address> {
	public IPv4SimpleRange() {
	}

	public IPv4SimpleRange(Inet4Address start, Inet4Address end) {
		setStart(start);
		setEnd(end);
	}

	public boolean isInRange(Inet4Address obj) {
		return NetUtil.isInTheIPV4Range((Inet4Address) getStart(), (Inet4Address) getEnd(), obj);
	}

	public String toString() {
		return "[" + ((Inet4Address) getStart()).getHostAddress() + "," + ((Inet4Address) getEnd()).getHostAddress()
				+ "]";
	}
}
