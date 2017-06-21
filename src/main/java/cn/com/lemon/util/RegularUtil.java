package cn.com.lemon.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <code>RegularUtil</code>class is basic regular expression utilities.
 * 
 * @see Matcher
 * @see Pattern
 * @author shishb
 * @version 1.0
 */
public class RegularUtil {
	public static boolean matchNumeric(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	public static boolean matchLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	public static boolean matchChinese(String str) {
		String regex = "[^x00-xff]+$";
		return match(regex, str);
	}

	public static boolean matchSpace(String str) {
		String regex = "\\s*|\t|\r|\n";
		return match(regex, str);
	}

	public static boolean matchEmail(String str) {
		String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		return match(regex, str);
	}

	public static boolean matchURL(String str) {
		String regex = "[a-zA-z]+://[^\\s]*";
		return match(regex, str);
	}

	public static boolean matchPhone(String str) {
		String regex = "^1[3|4|5|8][0-9]\\d{8}$";
		return match(regex, str);
	}

	public static boolean match18IDCard(String str) {
		String regex = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
		return match(regex, str);
	}

	public static boolean matchIDCard(String str) {
		String regex = "^\\d{15}|\\d{18}$";
		return match(regex, str);
	}

	public static boolean matchHtml(String str) {
		String regex = "<(.*)>(.*)<\\/(.*)>|<(.*)\\/>";
		return match(regex, str);
	}

	public static boolean matchPostcode(String str) {
		String regex = "^[1-9]\\d{5}$";
		return match(regex, str);
	}

	public static boolean matchAccount(String str) {
		String regex = "^[A-Za-z][A-Za-z1-9_-]+$";
		return match(regex, str);
	}

	public static boolean matchPassword(String str) {
		String regex = "^[A-Za-z0-9]+$";
		return match(regex, str);
	}

	public static boolean matchQQ(String str) {
		String regex = "^[1-9]\\d{4,10}$ ";
		return match(regex, str);
	}

	public static boolean matchMoney(String str) {
		String regex = "^(|\\-)[0-9]+(.[0-9]{1,3})?$";
		return match(regex, str);
	}

	private static boolean match(String regex, String str) {
		if ((str == null) || (str.equals(""))) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
