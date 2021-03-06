package cn.com.lemon.base;

import static cn.com.lemon.base.Preasserts.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>StringUtil</code>class is basic string utilities.
 * 
 * @see UnsupportedEncodingException
 * @see MessageDigest
 * @author shellpo shih
 * @version 1.0
 */
public final class Strings {
	private static final Logger LOG = LoggerFactory.getLogger(Strings.class.getName());
	private static final Charset GBK = Charset.forName("GBK");
	private static final Charset UTF8 = Charset.forName("UTF-8");

	private Strings() {
	}

	/**
	 * Return the plain text information
	 * 
	 * @param html
	 *            rich text message
	 * @return {@link String}
	 */
	public static String text(String html) {
		ParserDelegator pd = new ParserDelegator();
		Reader r = new StringReader(html);
		final StringBuilder sb = new StringBuilder();
		try {
			pd.parse(r, new HTMLEditorKit.ParserCallback() {
				public void handleText(char[] data, int pos) {
					sb.append(data);
				}
			}, false);
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 
	 * Returns {@code String[} change List to {@code String[]}
	 * 
	 * @param array
	 *            the {@code String} List
	 * @return {@code String[]}
	 */
	public static String[] toArray(List<String> list) {
		if (null == list || list.size() == 0)
			return null;
		String[] target = new String[list.size()];
		return list.toArray(target);
	}

	/**
	 * 
	 * Returns {@code String[} change List to {@code String[]}
	 * 
	 * @param array
	 *            the {@code String} List
	 * @return {@code String[]}
	 */
	public static String[] toArray(Set<String> list) {
		if (null == list || list.size() == 0)
			return null;
		String[] target = new String[list.size()];
		return list.toArray(target);
	}

	/**
	 * 
	 * Returns {@code List} change array to {@code List}
	 * 
	 * @param array
	 *            the {@code String} array
	 * @return {@code List}
	 */
	public static List<String> toList(String[] array) {
		if (null == array || array.length == 0)
			return null;
		return Arrays.asList(array);
	}

	/**
	 * Returns {@code true} if {@code target} is present as an element anywhere
	 * in {@code array}.
	 *
	 * @param array
	 *            an array of {@code String} values, possibly empty
	 * @param target
	 *            a primitive {@code String} value
	 * @return {@code true} if {@code array[i] == target} for some value of
	 *         {@code
	 *     i}
	 */
	public static boolean contains(String[] array, String target) {
		for (String value : array) {
			if (value == target) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Retrieves the <code>Blob</code> value designated by this
	 * <code>Blob</code> object as an string character.
	 *
	 * @param blob
	 *            <code>Blob</code> the database blob data
	 * @return a <code>String</code> object containing the <code>Blob</code>
	 *         data
	 * 
	 */
	public static String blob(Blob blob) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(blob.getBinaryStream(), UTF8.name()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			if (reader != null) {
				reader.close();
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retrieves the <code>Clob</code> value designated by this
	 * <code>Clob</code> object as an string character.
	 *
	 * @param clob
	 *            <code>Clob</code> the database blob data
	 * @return a <code>String</code> object containing the <code>Clob</code>
	 *         data
	 * 
	 */
	public static String clob(Clob clob) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(clob.getAsciiStream(), UTF8.name()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			if (reader != null) {
				reader.close();
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Return the file suffix {@code String} value
	 * 
	 * @param fileName
	 *            the file name{@code String} (for example a.jpg)
	 * @param isContainDot
	 *            the file suffix value is contain dot
	 * @return {@code String} the suffix value
	 */
	public static String suffix(String fileName, boolean isContainDot) {
		checkNotNull(fileName);
		if (isContainDot)
			return fileName.substring(fileName.lastIndexOf("."));
		else
			return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * Returns the given string if it is non-null; the empty string otherwise.
	 *
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is non-null; {@code ""} if it is null
	 */
	public static String nullToEmpty(String string) {
		return (string == null) ? "" : string;
	}

	/**
	 * Returns the given string if it is nonempty; {@code null} otherwise.
	 *
	 * @param string
	 *            the string to test and possibly return
	 * @return {@code string} itself if it is nonempty; {@code null} if it is
	 *         empty or null
	 */
	public static String emptyToNull(String string) {
		return isNullOrEmpty(string) ? null : string;
	}

	/**
	 * Returns {@code true} if the given string is null or is the empty string.
	 * 
	 * @param string
	 * @return {@code true} if the string is null or is the empty string
	 */
	public static boolean isNullOrEmpty(String string) {
		return string == null || string.length() == 0;
	}

	/**
	 * Returns {@code true} if the given string is contain Chinese string.
	 * 
	 * @param string
	 * @return ({@code true} if the string is contain Chinese string
	 */
	public static boolean isContainChinese(String string) {
		assert string != null;
		if (isNullOrEmpty(string))
			return false;
		try {
			return string.getBytes(UTF8.name()).length - string.length() > 0 ? true : false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Append the {@code target} to {@code source} and Return.
	 * 
	 * @param source
	 * @param target
	 * @return ({@code String} the combinatorial string
	 */
	public static String appendString(String source, String target) {
		return new StringBuilder().append(source).append(target).toString();
	}

	/**
	 * Returns {@code int} if the given string is contain Chinese string
	 * 
	 * @param string
	 * @return ({@code int} if the string is contain Chinese string
	 */
	public static int countContainChinese(String string) {
		assert string != null;
		if (isNullOrEmpty(string))
			return 0;
		try {
			if (isContainChinese(string)) {
				int byteInt = string.getBytes(UTF8.name()).length;
				int stringInt = string.length();
				return (byteInt - stringInt) / 2;
			} else {
				return 0;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Returns {@code String} the sub String by the given string
	 * <p>
	 * special attention the Chinese string,different charsetName has different
	 * length
	 * 
	 * @param string
	 * @param length
	 * @return ({@code String} the sub string
	 */
	public static String subString(String string, int length) {
		return subString(string, length, null, GBK.name());
	}

	/**
	 * Returns a string containing the supplied {@code String} values separated
	 * by {@code separator}. For example, {@code join("-", "a", "b", "c")}
	 * returns the string {@code "a-b-c"}.
	 *
	 * @param separator
	 *            the text that should appear between consecutive values in the
	 *            resulting string (but not at the start or end)
	 * @param array
	 *            an array of {@code String} values, possibly empty
	 */
	public static String join(String separator, String... array) {
		checkNotNull(separator);
		if (array.length == 0) {
			return "";
		}
		// For pre-sizing a builder, just get the right order of magnitude
		StringBuilder builder = new StringBuilder(array.length * 5);
		builder.append(array[0]);
		for (int i = 1; i < array.length; i++) {
			builder.append(separator).append(array[i]);
		}
		return builder.toString();
	}

	/**
	 * Returns a array {@code int} values split by {@code separator}. For
	 * example, {@code split("-", "a", "b", "c")} returns the string
	 * {@code "{a,b,c}"}.
	 *
	 * @param separator
	 *            the text that should appear between consecutive values in the
	 *            resulting string (but not at the start or end)
	 * @param array
	 *            an string of {@code String} values, possibly empty
	 */
	public static String[] split(String separator, String array) {
		checkNotNull(separator);
		if (null != array && array.length() == 0) {
			return null;
		}
		int size = array.split(separator).length;
		String[] result = new String[size];
		for (int i = 0; i < size; i++) {
			result[i] = array.split(separator)[i];
		}
		return result;
	}

	/**
	 * Returns {@code String} the sub String by the given string
	 * <p>
	 * special attention the Chinese string,different charsetName has different
	 * length
	 * 
	 * @param string
	 * @param length
	 * @param suffix
	 * @param charsetName
	 * @return ({@code String} the sub string
	 */
	public static String subString(String string, int length, String suffix, String charsetName) {
		LOG.debug("content[" + string + "],length[" + length + "],suffix[" + suffix + "],charset[" + charsetName + "]");
		assert string != null;
		if (isNullOrEmpty(string)) {
			return null;
		}
		suffix = isNullOrEmpty(suffix) ? "" : suffix;
		charsetName = isNullOrEmpty(charsetName) ? GBK.name() : charsetName;
		int size = string.length();
		if (/* Is contain Chinese characters */countContainChinese(string) > 0) {
			try {
				int count = string.getBytes(charsetName).length;
				if (count <= length) {
					return string;
				} else {
					// Remove redundant characters
					int tmp = length;
					String result = string.substring(0, length > size ? size : length);
					while (result.getBytes(charsetName).length > tmp) {
						length--;
						result = string.substring(0, length > size ? size : length);
					}
					return result + suffix;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return size >= length ? string.substring(0, length) + suffix : string;
		}
	}

	/**
	 * Encrypt the given string by Md5
	 * 
	 * @param string
	 * @return {@code String}
	 */
	public static String md5(String string) {
		checkNotNull(string);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			byte[] s = md.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result = result + Integer.toHexString(0xFF & s[i] | 0xFFFFFF00).substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Create UUID {@code String}
	 * 
	 * @return {@code String}
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toLowerCase();
	}

	/**
	 * Get client ip address
	 * 
	 * @param request
	 * @return
	 */
	public static String ip(HttpServletRequest request) {
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					ipAddress = null;
				}
			}
		}
		String ipSeparate = ",";
		int ipLength = 15;
		if (ipAddress != null && ipAddress.length() > ipLength) {
			if (ipAddress.indexOf(ipSeparate) > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(ipSeparate));
			}
		}
		return ipAddress;
	}

	/**
	 * Get the local server IP address
	 * 
	 * @return {@code String} {@code Inet4Address} address
	 */
	public static String ip() {
		try {
			Enumeration<?> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface i = (NetworkInterface) en.nextElement();
				for (Enumeration<?> en2 = i.getInetAddresses(); en2.hasMoreElements();) {
					InetAddress addr = (InetAddress) en2.nextElement();
					if (!addr.isLoopbackAddress()) {
						if (addr instanceof Inet4Address) {
							return addr.getHostAddress();
						}
					}
				}
			}
		} catch (SocketException e) {
			return null;
		}
		return null;
	}
}
