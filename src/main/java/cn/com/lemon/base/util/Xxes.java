package cn.com.lemon.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * Simple filter XXE third URL and sensitive words
 * 
 * @author shaobo shih
 * @version 1.0
 */
public final class Xxes {
	private final static Pattern URL_PATTERN = Pattern
			.compile("\\b(([\\w-]+://?|www[.])[^\\s()<>]+(?:[\\w\\d]+|([^[:punct:]\\s]|/)))");

	private Xxes() {
	}

	public static String xxe(String content) {
		if (isNullOrEmpty(content))
			return null;
		// filter third URL
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
		// filter sensitive word
		if (!isNullOrEmpty(content)) {
			content = content.replace("DOCTYPE", "").replace("SYSTEM", "").replace("ENTITY", "").replace("PUBLIC", "");
		}
		return content;
	}
}
