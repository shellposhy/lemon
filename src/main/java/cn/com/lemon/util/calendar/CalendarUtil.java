package cn.com.lemon.util.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.com.lemon.common.enums.date.EDateType;
import static cn.com.lemon.base.Strings.isNullOrEmpty;

/**
 * The <code>CalendarUtil</code> class is the base <code>Calendar</code>
 * utilities
 * 
 * @see Date
 * @see SimpleDateFormat
 * @see Calendar
 * @author shellpo shih
 * @version 1.0
 */
public final class CalendarUtil {

	private CalendarUtil() {
	}

	/* ====================================== */
	/* ==============common utilities=========== */
	/* ===================================== */
	private static Date convert(Date date, String format) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			String dateStr = dateFormat.format(date);
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Date convert(String param, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(param);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* ============== Date=========== */
	public static Date dateCurrent(EDateType dateType) {
		switch (dateType) {
		default:
		case Common:
			return convert(new Date(), "yyyy-MM-dd");
		case Simple:
			return convert(new Date(), "yyyyMMdd");
		case Chinese:
			return convert(new Date(), "yyyy年MM月dd日");
		}
	}

	public static Date dateTimeCurrent(EDateType dateType) {
		switch (dateType) {
		default:
		case Common:
			return convert(new Date(), "yyyy-MM-dd HH:mm:ss");
		case Simple:
			return convert(new Date(), "yyyyMMddHHmmss");
		case Chinese:
			return convert(new Date(), "yyyy年MM月dd日 HH时mm分ss秒");
		}
	}

	public static Date dateString(String dateStr) {
		return convert(dateStr, "yyyy-MM-dd");
	}

	public static Date dateTimeString(String dateStr) {
		return convert(dateStr, "yyyy-MM-dd HH:mm:ss");
	}

	/* ====================================== */
	/* ==============common utilities=========== */
	/* ===================================== */
	private static String change(int count, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = new GregorianCalendar();
		calendar.add(5, -count);
		return dateFormat.format(calendar.getTime());
	}

	private static String change(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	private static String change(String dateStr, String source, String target) {
		Date date = convert(dateStr, source);
		return change(date, target);
	}

	/* ==============Yesterday String=========== */
	public static String stringYesterdayDate(EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(1, "yyyy-MM-dd");
		case Simple:
			return change(1, "yyyyMMdd");
		case Chinese:
			return change(1, "yyyy年MM月dd日");
		default:
			return change(1, "yyyy-MM-dd");
		}
	}

	public static String stringYesterdayDatetime(EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(1, "yyyy-MM-dd HH:mm:ss");
		case Simple:
			return change(1, "yyyyMMddHHmmss");
		case Chinese:
			return change(1, "yyyy年MM月dd日 HH时mm分ss秒");
		default:
			return change(1, "yyyy-MM-dd HH:mm:ss");
		}
	}

	/* ==============Diff Date String=========== */
	public static String stringDiffDate(int num, EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(num, "yyyy-MM-dd");
		case Simple:
			return change(num, "yyyyMMdd");
		case Chinese:
			return change(num, "yyyy年MM月dd日");
		default:
			return change(num, "yyyy-MM-dd");
		}
	}

	public static String stringDiffDateime(int num, EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(num, "yyyy-MM-dd HH:mm:ss");
		case Simple:
			return change(num, "yyyyMMddHHmmss");
		case Chinese:
			return change(num, "yyyy年MM月dd日 HH时mm分ss秒");
		default:
			return change(num, "yyyy-MM-dd HH:mm:ss");
		}
	}

	/* ==============Current Date String=========== */
	public static String stringCurrentDate(EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(new Date(), "yyyy-MM-dd");
		case Simple:
			return change(new Date(), "yyyyMMdd");
		case Chinese:
			return change(new Date(), "yyyy年MM月dd日");
		default:
			return change(new Date(), "yyyy-MM-dd");
		}
	}

	public static String stringCurrentDatetime(EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(new Date(), "yyyy-MM-dd HH:mm:ss");
		case Simple:
			return change(new Date(), "yyyyMMddHHmmss");
		case Chinese:
			return change(new Date(), "yyyy年MM月dd日 HH时mm分ss秒");
		default:
			return change(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
	}

	public static String stringCalendarFormat(String dateStr, String source, String target) {
		return change(dateStr, source, target);
	}

	public static String stringDateTime(Date date, EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(date, "yyyy-MM-dd HH:mm:ss");
		case Simple:
			return change(date, "yyyyMMddHHmmss");
		case Chinese:
			return change(date, "yyyy年MM月dd日 HH时mm分ss秒");
		default:
			return change(date, "yyyy-MM-dd HH:mm:ss");
		}
	}

	public static String stringDate(Date date, EDateType dateType) {
		switch (dateType) {
		case Common:
			return change(date, "yyyy-MM-dd");
		case Simple:
			return change(date, "yyyyMMdd");
		case Chinese:
			return change(date, "yyyy年MM月dd日");
		default:
			return change(date, "yyyy-MM-dd");
		}
	}

	public static String stringTime(Date date) {
		return change(date, "HH:mm:ss");
	}

	/* ====================================== */
	/* ================= utilities=============== */
	/* ===================================== */
	public static int getYear(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getMonth(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(2) + 1;
	}

	public static int getDay(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getHour(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(11);
	}

	public static int getMinute(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(12);
	}

	public static int getSecond(Date date) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(13);
	}

	public static int diffDates(Date from, Date to) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(from);
		int start = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(to);
		int end = aCalendar.get(Calendar.DAY_OF_YEAR);
		return end - start;
	}

	public static boolean isDateString(String dateString, String dateFormat) {
		if (isNullOrEmpty(dateString) || isNullOrEmpty(dateFormat)) {
			return false;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);
			format.setLenient(false);
			format.parse(dateString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* ====================================== */
	/* =============calculate utilities============ */
	/* ===================================== */
	public static String getCurrentFirstDate(EDateType dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		switch (dateType) {
		default:
		case Common:
			return change(calendar.getTime(), "yyyy-MM-dd");
		case Simple:
			return change(calendar.getTime(), "yyyyMMdd");
		case Chinese:
			return change(calendar.getTime(), "yyyy年MM月dd日");
		}
	}

	public static String getCurrentLastDate(EDateType dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		switch (dateType) {
		default:
		case Common:
			return change(calendar.getTime(), "yyyy-MM-dd");
		case Simple:
			return change(calendar.getTime(), "yyyyMMdd");
		case Chinese:
			return change(calendar.getTime(), "yyyy年MM月dd日");
		}
	}

	public static String getFirstDate(int year, int month, EDateType dateType) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
		switch (dateType) {
		default:
		case Common:
			return change(calendar.getTime(), "yyyy-MM-dd");
		case Simple:
			return change(calendar.getTime(), "yyyyMMdd");
		case Chinese:
			return change(calendar.getTime(), "yyyy年MM月dd日");
		}
	}

	public static String getLastDate(int year, int month, EDateType dateType) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		switch (dateType) {
		default:
		case Common:
			return change(cal.getTime(), "yyyy-MM-dd");
		case Simple:
			return change(cal.getTime(), "yyyyMMdd");
		case Chinese:
			return change(cal.getTime(), "yyyy年MM月dd日");
		}
	}
}
