package cn.com.lemon.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程安全的日期处理类
 * 
 * @author shishb
 * @version 1.0
 */
public final class DateUtil {
	private DateUtil() {
	}

	private static final Object LOCK = new Object();
	private static Map<String, ThreadLocal<SimpleDateFormat>> SAFYMAP = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	/**
	 * 按照指定日期格式输出日期字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return init(pattern).format(date);
	}

	/**
	 * 把日期字符串输出为制定格式日期
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parse(String dateStr, String pattern) {
		try {
			return init(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* =================私有工具类======================= */
	private static SimpleDateFormat init(final String format) {
		ThreadLocal<SimpleDateFormat> local = SAFYMAP.get(format);
		if (local == null) {
			synchronized (LOCK) {
				local = SAFYMAP.get(format);
				if (local == null) {
					local = new ThreadLocal<SimpleDateFormat>() {
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(format);
						}
					};
					SAFYMAP.put(format, local);
				}
			}
		}
		return local.get();
	}
}
