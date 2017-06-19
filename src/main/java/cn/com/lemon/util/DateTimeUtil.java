package cn.com.lemon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeUtil {
	public static Date getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String tmp = sdf.format(now);
		try {
			return sdf.parse(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String tmp = sdf.format(now);
		try {
			return sdf.parse(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getYesterday() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cpcalendar = new GregorianCalendar();
		cpcalendar.add(5, -1);
		String now = format.format(cpcalendar.getTime());
		return now;
	}

	public static String getYesterdayShort() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cpcalendar = new GregorianCalendar();
		cpcalendar.add(5, -1);
		String now = format.format(cpcalendar.getTime());
		return now;
	}

	public static String getDiffDateShort(int days) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cpcalendar = new GregorianCalendar();
		cpcalendar.add(5, -days);
		String now = format.format(cpcalendar.getTime());
		return now;
	}

	public static String getCurrentDateString() {
		return getCurrentDateTimeString("yyyy-MM-dd");
	}

	public static String getCurrentDateTimeString() {
		return getCurrentDateTimeString("yyyy-MM-dd HH:mm:ss");
	}

	public static String getCurrentDateTimeString(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		return sdf.format(now);
	}

	public static Date parse(String param) {
		return parse(param, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date parseShort(String param) {
		return parse(param, "yyyy-MM-dd");
	}

	public static Date parse(String param, String format) {
		Date date = null;
		if (param == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(param);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date;
	}

	public static boolean isDataString(String param) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			sdf.parse(param);
			return true;
		} catch (ParseException ex) {
		}
		return false;
	}

	public static String formatDateTimeStr(String param) {
		return formatDateTimeStr(param, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDateTimeStr(String param, String srcFormat, String tarFormat) {
		Date date = parse(param, srcFormat);
		return format(date, tarFormat);
	}

	public static String formatDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String formatTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date_str = "";
		try {
			date_str = sdf.format(date);
		} catch (Exception localException) {
		}
		return date_str;
	}

	public static String time2String(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String date_str = "";
		try {
			date_str = sdf.format(date);
		} catch (Exception localException) {
		}
		return date_str;
	}

	public static Map<Long, Integer> getYearAndMonth(Integer monthCount) {
		Map<Long, Integer> map = new HashMap<Long, Integer>();
		map.put(Long.valueOf(0L), Integer.valueOf(Math.abs(monthCount.intValue() / 12)));
		map.put(Long.valueOf(1L), Integer.valueOf(monthCount.intValue() % 12));
		return map;
	}

	public static Date getAfterDate(Date date, int year, int month, int day) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();

		cal.setTime(date);
		if (year != 0) {
			cal.add(1, year);
		}
		if (month != 0) {
			cal.add(2, month);
		}
		if (day != 0) {
			cal.add(5, day);
		}
		return cal.getTime();
	}

	public static int getCurrentYear() {
		Calendar cal = new GregorianCalendar();
		return cal.get(1);
	}

	public static int getCurrentMonth() {
		Calendar cal = new GregorianCalendar();
		return cal.get(2) + 1;
	}

	public static int getCurrentDay() {
		Calendar cal = new GregorianCalendar();
		return cal.get(5);
	}

	public static int getCurrentHour() {
		Calendar cal = new GregorianCalendar();
		return cal.get(11);
	}

	public static int getCurrentMinute() {
		Calendar cal = new GregorianCalendar();
		return cal.get(12);
	}

	public static int getCurrentSecond() {
		Calendar cal = new GregorianCalendar();
		return cal.get(13);
	}

	public static int getYear(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getMonth(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(2);
	}

	public static int getLastDayOfMonth(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.getActualMaximum(5);
	}

	public static int getDay(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getHour(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(11);
	}

	public static int getMinute(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(12);
	}

	public static int getSecond(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal.get(13);
	}

	public static long getDaysOfDate(Date d1, Date d2) {
		double c = (d1.getTime() - d2.getTime()) / 1000.0D / 3600.0D / 24.0D;
		return (long) Math.floor(c);
	}

	public static long getHoursOfDate(Date d1, Date d2) {
		double c = (d1.getTime() - d2.getTime()) / 1000.0D / 3600.0D;
		return (long) Math.floor(c);
	}

	public static long getMinutesOfDate(Date d1, Date d2) {
		double c = (d1.getTime() - d2.getTime()) / 1000.0D / 60.0D;
		return (long) Math.floor(c);
	}

	public static long getSecondsOfDate(Date d1, Date d2) {
		double c = (d1.getTime() - d2.getTime()) / 1000.0D;
		return (long) Math.floor(c);
	}

	public static int getDaysOfMonth() {
		return getDaysOfMonth(getCurrentYear(), getCurrentMonth());
	}

	public static int getDaysOfMonth(int year, int month) {
		return (int) ((toLongTime(month == 12 ? year + 1 : year, month == 12 ? 1 : month + 1, 1)
				- toLongTime(year, month, 1)) / 86400000L);
	}

	public static int getDaysOfNextMonth(int year, int month) {
		year = month == 12 ? year + 1 : year;
		month = month == 12 ? 1 : month + 1;
		return getDaysOfMonth(year, month);
	}

	public static int getDaysOfPreMonth(int year, int month) {
		year = month == 1 ? year - 1 : year;
		month = month == 1 ? 12 : month - 1;
		return getDaysOfMonth(year, month);
	}

	public static String getFirstDayOfPreMonth(String dqrq) {
		String strDQRQ = dqrq;
		String strYear = "";
		String strMonth = "";
		String strDay = "";
		if (strDQRQ == null) {
			return "";
		}
		if (strDQRQ.length() == 8) {
			strYear = strDQRQ.substring(0, 4);
			strMonth = strDQRQ.substring(4, 6);
			strDay = strDQRQ.substring(6, 8);
		}
		if (strDQRQ.length() == 10) {
			strYear = strDQRQ.substring(0, 4);
			strMonth = strDQRQ.substring(5, 7);
			strDay = strDQRQ.substring(8, 10);
		}
		int iMonth = Integer.parseInt(strMonth);
		int iYear = Integer.parseInt(strYear);
		if (iMonth == 1) {
			iYear--;
			iMonth = 12;
		} else if (iMonth > 1) {
			iMonth--;
		} else {
			return "";
		}
		if (iMonth < 10) {
			strMonth = "0" + iMonth;
		} else {
			strMonth = String.valueOf(iMonth);
		}
		strDay = "01";
		if (strDQRQ.length() == 8) {
			return iYear + strMonth + strDay;
		}
		if (strDQRQ.length() == 10) {
			return iYear + "-" + strMonth + "-" + strDay;
		}
		return "";
	}

	public static long toLongTime(int year, int month, int day) {
		return toDate(year, month, day).getTime();
	}

	public static Date toDate(int year, int month, int day) {
		Calendar cld = new GregorianCalendar();
		cld.clear();
		cld.set(1, year);
		cld.set(2, month - 1);
		cld.set(5, day);
		return cld.getTime();
	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(7) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}

	public static Date getStartTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal.getTime();
	}

	public static Date getEndTimeOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal.getTime();
	}

	public static String getCurrentDateAndWeekString() {
		Date date = getCurrentDate();
		return format(date, "yyyy年M月d日 ") + getWeekOfDate(date);
	}

	public static Date getFirstDateOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		setTimeToZero(calendar);
		calendar.set(5, 1);
		return calendar.getTime();
	}

	public static Date getLastDateOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		setTimeToZero(calendar);
		return getLastDateOfMonth(calendar.getTime());
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getFirstDayOfNextMonth(date));
		calendar.set(5, calendar.get(5) - 1);

		return calendar.getTime();
	}

	public static Date getFirstDayOfNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, 1);
		calendar.set(2, calendar.get(2) + 1);
		return calendar.getTime();
	}

	public static Date getLastDateOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 7);
		return calendar.getTime();
	}

	public static int getDayOfMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(5);
	}

	public static int getDayOfWeek(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(7);
	}

	public static Date getFirstDateOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(7, 1);
		return calendar.getTime();
	}

	public static Date getTheNextDay(Date baseDate) {
		Calendar calendar = Calendar.getInstance();
		if (baseDate != null) {
			calendar.setTime(baseDate);
		}
		calendar.set(5, calendar.get(5) + 1);
		return calendar.getTime();
	}

	public static Date getDate(String dateTimeStr) throws ParseException {
		DateFormat df = DateFormat.getDateTimeInstance();
		return df.parse(getStandardDateTimeString(dateTimeStr));
	}

	public static String getStandardDateTimeString(String dateTimeStr) {
		Pattern number = Pattern.compile("\\d+");
		Matcher m = number.matcher(dateTimeStr);
		String[] matchs = { "00", "00", "00", "00", "00", "00" };
		int i = 0;
		while (m.find()) {
			matchs[(i++)] = dateTimeStr.substring(m.start(), m.end());
		}
		return matchs[0] + "-" + matchs[1] + "-" + matchs[2] + " " + matchs[3] + ":" + matchs[4] + ":" + matchs[5];
	}

	public static Date getDateOnly(Date date) {
		Calendar calendar = getCalendar(date);
		setTimeToZero(calendar);
		return calendar.getTime();
	}

	public static Date getDate(long timeLong) throws ParseException {
		return new Date(timeLong);
	}

	private static void setTimeToZero(Calendar c) {
		c.set(11, 0);
		c.set(12, 0);
		c.set(13, 0);
		c.set(14, 0);
	}

	private static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
}
