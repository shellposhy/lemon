package cn.com.lemon.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Thread safe date handling class
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
	 * Output the date string in the specified date format
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return init(pattern).format(date);
	}

	/**
	 * Output the date string as a formatted date
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

	/**
	 * Gets the month of the specified date
	 * 
	 * @param date
	 * @return int
	 */
	public static int month(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * Gets the day of the specified date
	 * 
	 * @param date
	 * @return int
	 */
	public static int day(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * Gets the year of the specified date
	 * 
	 * @param date
	 * @return int
	 */
	public static int year(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Get the number of days off the two dates
	 * 
	 * @param from
	 * @param to
	 * @return {@code Integer} days off the two dates
	 */
	public static int days(Date from, Date to) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(from);
		int start = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(to);
		int end = aCalendar.get(Calendar.DAY_OF_YEAR);
		return end - start;
	}

	/**
	 * Get the number of seconds off the two dates
	 * 
	 * @param from
	 * @param to
	 * @return {@code Long} seconds off the two dates
	 */
	public static Long seconds(Date from, Date to) {
		long result = (to.getTime() - from.getTime()) / 1000L;
		return Math.abs(result);
	}

	/* =================tools======================= */
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
