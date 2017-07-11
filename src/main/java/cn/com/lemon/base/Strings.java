package cn.com.lemon.base;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * The <code>StringUtil</code>class is basic string utilities.
 * 
 * @see UnsupportedEncodingException
 * @see MessageDigest
 * @author shellpo shih
 * @version 1.0
 */
public class Strings {

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
			return string.getBytes("UTF-8").length - string.length() > 0 ? true : false;
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
				int byteInt = string.getBytes("UTF-8").length;
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
		return subString(string, length, null, "GBK");
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
		assert string != null;
		if (isNullOrEmpty(string)) {
			return null;
		}
		suffix = isNullOrEmpty(suffix) ? "" : suffix;
		int chineseNum = countContainChinese(string);
		int size = string.length();
		if (chineseNum > 0) {
			try {
				int count = string.getBytes(charsetName).length;
				if (count <= length) {
					return string;
				} else {
					length = length - suffix.getBytes(charsetName).length;
					int tmp = length;
					if (length > 0) {
						String result = string.substring(0, length);
						while (result.getBytes(charsetName).length > tmp) {
							length--;
							result = string.substring(0, length);
						}
						return result + suffix;
					} else {
						return suffix;
					}
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
	public static String MD5(String string) {
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
		}
		return null;
	}

}
