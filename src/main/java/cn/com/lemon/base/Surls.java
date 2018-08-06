package cn.com.lemon.base;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Simple static methods to be called at the start of visit web site by short
 * URL correct arguments and state.
 * 
 * <p>
 * It also provides a short connection method based on baidu API interface.
 * 
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Surls {

	private final static String[] CHARS = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	private final static String DEFAULT_KEY = "SURL";

	private Surls() {
	}

	/**
	 * Short connection code is produced based on MD5 encryption. <br>
	 * Obfuscate and encrypt the link address that generated md5, and use the
	 * default {@code DEFAULT_KEY} value if the key is empty.
	 * 
	 * @param url
	 *            URL address
	 * @param key
	 *            the Encryption confusion value
	 * @return {@code String[]} select a value in the array as a short URL value
	 */
	public static String[] url(String url, String key) {
		if (isNullOrEmpty(url))
			return null;
		if (isNullOrEmpty(key))
			key = DEFAULT_KEY;
		String[] result = new String[4];
		String md5Url = DigestUtils.md5Hex(key + url);
		for (int i = 0; i < 4; i++) {
			String md5SubString = md5Url.substring(i * 8, i * 8 + 8);
			long hexLong = 0x3FFFFFFF & Long.parseLong(md5SubString, 16);
			String shortCode = "";
			for (int j = 0; j < 6; j++) {
				long index = 0x0000003D & hexLong;
				shortCode += CHARS[(int) index];
				hexLong = hexLong >> 5;
			}
			result[i] = shortCode;
		}
		return result;
	}
}
