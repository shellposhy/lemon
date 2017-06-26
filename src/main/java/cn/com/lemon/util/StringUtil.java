package cn.com.lemon.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import cn.com.lemon.http.HtmlUtil;

public class StringUtil {
	public static final String EMPTY = "";

	public static boolean isEmpty(String str) {
		return (str == null) || (str.isEmpty());
	}

	public static boolean isEmptyAfterTrim(String str) {
		return (isEmpty(str)) || (str.trim().length() == 0);
	}

	public static String substringBefore(String str, String separator) {
		if ((isEmptyAfterTrim(str)) || (separator == null)) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = indexOfIgnoreCase(str, separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.trim().equals(""))) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = indexOfIgnoreCase(str, separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static int indexOfIgnoreCase(String str, String searchStr) {
		return indexOfIgnoreCase(str, searchStr, 0);
	}

	public static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
		int spos = startPos;
		if ((str == null) || (searchStr == null)) {
			return -1;
		}
		if (spos < 0) {
			spos = 0;
		}
		int endLimit = str.length() - searchStr.length() + 1;
		if (spos > endLimit) {
			return -1;
		}
		if (searchStr.length() == 0) {
			return spos;
		}
		for (int i = spos; i < endLimit; i++) {
			if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
				return i;
			}
		}
		return -1;
	}

	public static String combineToString(String[] strArray, String separator) {
		StringBuilder result = new StringBuilder();
		String[] arrayOfString = strArray;
		int j = strArray.length;
		for (int i = 0; i < j; i++) {
			String str = arrayOfString[i];
			result.append(str).append(separator);
		}
		if (result.length() > 0) {
			result.setLength(result.length() - 1);
		}
		return result.toString();
	}

	public static String combineToString(List<String> strList, String separator) {
		return combineToString((String[]) strList.toArray(new String[strList.size()]), separator);
	}

	public static String toStringFromThrowableWithStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	public static String subStringByByte(String resourseStr, int subLength) {
		return subStringByByte(resourseStr, subLength, null);
	}

	public static String subStringByByte(String resourseStr, int subLength, String suffix) {
		if ((resourseStr == null) || (resourseStr.equals(""))) {
			return resourseStr;
		}
		if (resourseStr.getBytes().length <= subLength) {
			return resourseStr;
		}
		int tempSubLength = subLength;
		if (suffix != null) {
			tempSubLength -= suffix.length();
		}
		int tempLength = resourseStr.length() > subLength ? subLength : resourseStr.length();
		String subStr = resourseStr.substring(0, tempLength);
		try {
			int subStrByetsL = subStr.getBytes("GBK").length;
			while (subStrByetsL > tempSubLength) {
				subLength--;
				int subSLengthTemp = subLength;
				subStr = subStr.substring(0, subSLengthTemp > subStr.length() ? subStr.length() : subSLengthTemp);
				subStrByetsL = subStr.getBytes("GBK").length;
			}
			if ((suffix != null) && (resourseStr.length() > subStr.length())) {
			}
			return subStr + suffix;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (StringIndexOutOfBoundsException se) {
			se.printStackTrace();
		}
		return null;
	}

	public static String toString(Blob blob) throws UnsupportedEncodingException, SQLException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(blob.getBinaryStream(), "utf-8"));
		String s = null;
		StringBuilder sb = new StringBuilder();
		while ((s = br.readLine()) != null) {
			sb.append(s);
		}
		return sb.toString();
	}

	public static String firstLetterToUpperCase(String inputStr) {
		return String.valueOf(inputStr.charAt(0)).toUpperCase() + inputStr.substring(1);
	}

	public static String encodeToMD5(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(message.getBytes());

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

	public static String leftByteStr(String str, int n) {
		if (str == null) {
			return null;
		}
		int m = 0;
		for (int i = 0; i < str.length(); i++) {
			m += (String.valueOf(str.charAt(i)).getBytes().length >= 2 ? 2 : 1);
			if (m >= n) {
				return str.substring(0, i + 1);
			}
		}
		return str;
	}

	public static String leftByteHtmlStr(String str, int n) throws IOException {
		return HtmlUtil.subByteString(str, 0, n);
	}

	public static int byteLength(String str) {
		if (str == null) {
			return 0;
		}
		int m = 0;
		for (int i = 0; i < str.length(); i++) {
			m += (String.valueOf(str.charAt(i)).getBytes().length >= 2 ? 2 : 1);
		}
		return m;
	}

	public static int byteHtmlLength(String str) throws IOException {
		if (str == null) {
			return 0;
		}
		str = HtmlUtil.getText(str);
		int m = 0;
		for (int i = 0; i < str.length(); i++) {
			m += (String.valueOf(str.charAt(i)).getBytes().length >= 2 ? 2 : 1);
		}
		return m;
	}
}
