package cn.com.lemon.util;

import java.util.Date;

import static cn.com.lemon.base.DateUtil.parse;
import static cn.com.lemon.base.DateUtil.seconds;
import static cn.com.lemon.base.DateUtil.format;

/**
 * 官网首页数据处理工具类
 */
public class Statistics {

	private final static long MAX_SECONDS = 86400L;// one day
	private final static long EIGHT_SECONDS = 28800L;// 8 hours
	private final static long TWENTY_SECONDS = 72000L;// 20 hours

	private final static long DEFAULT_BASELINE = 56000000L;// baseLine
	private final static long DAY_BASELINE = 48000L;// day baseline

	private final static float MAX_RATIO = 0.8f;
	private final static float MIN_RATIO = 0.2f;

	private final static Date DEFAULT = parse("2018-09-04 00:00:00", "yyyy-MM-dd HH:mm:ss");
	private final static String FORMAT_SHORT = "yyyy-MM-dd";
	private final static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

	private Statistics() {
	}

	/**
	 * Simulate website visits
	 *
	 * @param baseLine
	 *            {@code Long} the base line data
	 * @param toCount
	 *            {@code Long} cumulative statistics
	 * @param dayCount
	 *            {@code Long} day count
	 * @return
	 */
	public static long number(long baseLine, long toCount, long dayCount) {
		long /* 累计到昨天的数据 */ result = baseLine + toCount;
		Date toDate = parse(format(new Date(), FORMAT_SHORT) + " 00:00:00", FORMAT_LONG);
		long seconds = seconds(toDate, new Date());
		/* 非高峰每秒数 */
		long increase = 0L;
		if (/* 8小时 */seconds <= EIGHT_SECONDS) {
			increase = (seconds * dayCount) / 216000;
		} else if /* 20小时 */ (seconds > EIGHT_SECONDS && seconds <= TWENTY_SECONDS) {
			increase = (dayCount * 2 / 15) + ((seconds - EIGHT_SECONDS) * dayCount) / 54000;
		} else {
			increase = ((dayCount * 2) / 15) + ((dayCount * 4) / 5)
					+ (((seconds - TWENTY_SECONDS) * dayCount) / 216000);
		}
		System.out.println("increase=" + increase);
		result = result + increase;
		return result;
	}

	/**
	 * Simulate website visits
	 *
	 * @param start
	 *            {@code Date} the start web site count date
	 * @param baseLine
	 *            {@code Long} the base line data
	 * @param dayBaseLine
	 *            {@code Long} the day base line data
	 * @return
	 */
	public static long number(Date start, Long baseLine, Long dayBaseLine) {
		// parameter
		if (null == start)
			start = DEFAULT;
		if (null == baseLine)
			baseLine = DEFAULT_BASELINE;
		if (null == dayBaseLine)
			dayBaseLine = DAY_BASELINE;
		// data process
		long result = 0L;
		long seconds = seconds(start, new Date());
		long days = seconds / MAX_SECONDS;
		long surplus = seconds % MAX_SECONDS;
		long increase = 0L;
		if (surplus < EIGHT_SECONDS) {
			increase = (long) (surplus * MIN_RATIO);
		} else if (surplus >= EIGHT_SECONDS && surplus < TWENTY_SECONDS) {
			increase = (DAY_BASELINE * 2) / (5 * 3) + (long) ((surplus - EIGHT_SECONDS) * MAX_RATIO);
		} else if (surplus >= TWENTY_SECONDS) {
			increase = ((DAY_BASELINE * 2) / (5 * 3)) + (DAY_BASELINE * 4 / 5)
					+ (long) ((surplus - TWENTY_SECONDS) * MIN_RATIO);
		}
		result = baseLine + increase + days * DAY_BASELINE;
		return result;
	}
}
