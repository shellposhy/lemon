package cn.com.lemon.base.tool;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Static utility methods pertaining to {@code Cookie} primitives.
 * <p>
 * The base utility contain basic operate by {@code Add},{@code Update} and
 * {@code Remove}
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Cookies {

	private Cookies() {
	}

	/**
	 * Write cookie value
	 * 
	 * @param response
	 * @param domain
	 * @param name
	 * @param value
	 * @param liveTime
	 * @return
	 */
	public static void add(HttpServletResponse response, String domain, String name, String value, int liveTime) {
		write(response, domain, name, value, liveTime);
	}

	public static void add(HttpServletResponse response, String domain, String name, String value) {
		// if the live time is null,the cookie default live time 20*60 seconds
		write(response, domain, name, value, 20 * 60);
	}

	/**
	 * Remove the cookie
	 * 
	 * @param response
	 * @param domain
	 * @param name
	 */
	public static void remove(HttpServletResponse response, String domain, String name) {
		write(response, domain, name, null, 0);
	}

	/**
	 * Return the {@code Cookie} by the {@code String} name
	 * 
	 * @param request
	 * @param name
	 * @return the {@code Cookie} value
	 */
	public static Cookie read(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/* ===================private utilities===================== */
	private static void write(HttpServletResponse response, String domain, String name, String value, int liveTime) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath("/");
		cookie.setMaxAge(liveTime);
		response.addCookie(cookie);
	}

	private static Map<String, Cookie> readMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
