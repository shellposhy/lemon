package cn.com.lemon.base;

import static cn.com.lemon.util.RegularUtil.matchPhone;
import static cn.com.lemon.util.RegularUtil.match15IDCard;
import static cn.com.lemon.util.RegularUtil.match18IDCard;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Static utility methods pertaining to {@code Desensitives} primitives.
 * <p>
 * The base utility contain basic operate by {@code phone},{@code id} and
 * {@code realname},order to data desensitization
 *
 * @author shellpo shih
 * @version 1.0
 */
public final class Desensitives {

	private Desensitives() {
	}

	/**
	 * Desensitize telephone number data.
	 * <P>
	 * Usually the phone number is encrypted with the middle four digits.
	 * <p>
	 * 13012345678 to 130****5678
	 * 
	 * @param content
	 *            the cell number
	 * @return {@link String} the desensitization number
	 */
	public static String phone(String content) {
		if (isNullOrEmpty(content))
			return content;
		if (matchPhone(content)) {
			return content.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
		}
		return content;
	}

	/**
	 * Desensitize bank number data.
	 * <P>
	 * Usually the phone number is encrypted with the middle digits.
	 * <p>
	 * 13012345678 to 130****5678
	 * 
	 * @param content
	 *            the cell number
	 * @return {@link String} the desensitization number
	 */
	public static String account(String content) {
		if (isNullOrEmpty(content))
			return content;
		int length = content.trim().length() - 8;
		return content.replaceAll("(\\d{4})\\d{" + length + "}(\\d{4})", "$1******$2");
	}

	/**
	 * Desensitize user id data.
	 * <P>
	 * Usually the id is encrypted with the middle data.
	 * <p>
	 * 110101195601251234 to 130****5678
	 * 
	 * @param content
	 *            the cell number
	 * @return {@link String} the desensitization number
	 */
	public static String id(String content) {
		if (isNullOrEmpty(content))
			return content;
		if (match15IDCard(content)) {
			return content.replaceAll("(\\d{4})\\d{7}(\\w{4})", "$1*****$2");
		}
		if (match18IDCard(content)) {
			return content.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
		}
		return content;
	}
}
