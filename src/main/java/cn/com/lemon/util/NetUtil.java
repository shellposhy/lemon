package cn.com.lemon.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Set;

import cn.com.lemon.common.exception.ParaErrorException;
import cn.com.lemon.common.framework.range.IPv4ComplexRange;
import cn.com.lemon.common.framework.range.IPv4SimpleRange;

public class NetUtil {
	public static Inet4Address toInet4Address(Short[] ip) throws ParaErrorException, UnknownHostException {
		int size = ip.length;
		if (size != 4) {
			throw new ParaErrorException("输入的IP地址不为4段。");
		}
		byte[] b = new byte[4];
		for (int i = 0; i < size; i++) {
			if ((ip[i].shortValue() < 0) || (ip[i].shortValue() > 255)) {
				throw new ParaErrorException("IP地址每段只能输入[0,255]内（包括）的数字。");
			}
			b[i] = ip[i].byteValue();
		}
		return (Inet4Address) Inet4Address.getByAddress(b);
	}

	public static Inet4Address toInet4Address(String ipStr) throws ParaErrorException, UnknownHostException {
		Short[] ip = new Short[4];
		try {
			String[] ss = ipStr.split("\\.");
			int size = ss.length;
			if (size != 4) {
				throw new ParaErrorException("输入的IP地址不为4段。");
			}
			for (int i = 0; i < size; i++) {
				ip[i] = Short.valueOf(ss[i]);
			}
		} catch (ParaErrorException e) {
			throw e;
		} catch (Exception e) {
			throw new ParaErrorException("输入的字符串不是IP字符串。" + e);
		}
		return toInet4Address(ip);
	}

	public static boolean isInTheIPV4Range(Inet4Address startIp, Inet4Address endIp, Inet4Address testIp) {
		byte[] startBytes = startIp.getAddress();
		byte[] endBytes = endIp.getAddress();
		byte[] testBytes = testIp.getAddress();
		long start = 0L;
		long end = 0L;
		long test = 0L;
		start |= startBytes[3] & 0xFF;
		start |= (startBytes[2] & 0xFF) << 8 & 0xFF00;
		start |= (startBytes[1] & 0xFF) << 16 & 0xFF0000;
		start |= (startBytes[0] & 0xFF) << 24 & 0xFF000000;
		end |= endBytes[3] & 0xFF;
		end |= (endBytes[2] & 0xFF) << 8 & 0xFF00;
		end |= (endBytes[1] & 0xFF) << 16 & 0xFF0000;
		end |= (endBytes[0] & 0xFF) << 24 & 0xFF000000;
		test |= testBytes[3] & 0xFF & 0xFF;
		test |= (testBytes[2] & 0xFF) << 8 & 0xFF00;
		test |= (testBytes[1] & 0xFF) << 16 & 0xFF0000;
		test |= (testBytes[0] & 0xFF) << 24 & 0xFF000000;
		System.out.println(start);
		System.out.println(end);
		System.out.println(test);
		if ((start <= test) && (test <= end)) {
			return true;
		}
		return false;
	}

	public static String getIPV4String(Inet4Address ip) {
		return ip.getHostAddress();
	}

	public static IPv4SimpleRange getNewIPv4SimpleRange(String rangeStr)
			throws UnknownHostException, ParaErrorException {
		String[] ips = rangeStr.split(",");
		Inet4Address start = toInet4Address(ips[0].substring(2));
		Inet4Address end = toInet4Address(ips[1].substring(0, ips[1].length() - 1));
		return new IPv4SimpleRange(start, end);
	}

	public static IPv4ComplexRange getNewIPv4ComplexRange(String rangeStr)
			throws UnknownHostException, ParaErrorException {
		String[] ranges = rangeStr.split(";");
		IPv4ComplexRange compleRange = new IPv4ComplexRange();
		for (String r : ranges) {
			if (r.startsWith("+")) {
				compleRange.getPositiveSet().add(getNewIPv4SimpleRange(r));
			} else if (r.startsWith("-")) {
				compleRange.getMinusSet().add(getNewIPv4SimpleRange(r));
			}
		}
		return compleRange;
	}

	public static String getComplexRangeString(IPv4ComplexRange range) {
		Set<IPv4SimpleRange> pSet = range.getPositiveSet();
		Set<IPv4SimpleRange> mSet = range.getMinusSet();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		if (pSet != null) {
			for (IPv4SimpleRange ipRange : pSet) {
				if (i > 0) {
					sb.append(";");
				}
				sb.append("+");
				sb.append(ipRange);
			}
		}
		if (mSet != null) {
			for (IPv4SimpleRange ipRange : mSet) {
				if (i > 0) {
					sb.append(";");
				}
				sb.append("+");
				sb.append(ipRange);
			}
		}
		return sb.toString();
	}
}