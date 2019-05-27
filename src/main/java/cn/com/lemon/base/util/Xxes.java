package cn.com.lemon.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Xxes {
	private final static Pattern URL_PATTERN = Pattern
			.compile("\\b(([\\w-]+://?|www[.])[^\\s()<>]+(?:[\\w\\d]+|([^[:punct:]\\s]|/)))");

	private Xxes() {
	}

	public static String xxe(String content) {
		Matcher matcher = URL_PATTERN.matcher(content);
		if (matcher.find()) {
			if (matcher.groupCount() == 1) {
				content = content.replace(matcher.group(0), "");
			} else {
				for (int i = 0; i < matcher.groupCount(); i++) {
					content = content.replace(matcher.group(i), "");
				}
			}
		}
		if (content != null && content.length() > 0) {
			content = content.replace("DOCTYPE", "").replace("SYSTEM", "").replace("ENTITY", "").replace("PUBLIC", "");
		}
		return content;
	}
}
