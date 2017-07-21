package cn.com.lemon.util.calendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The <code>ChineseCalendarUtil</code> class is the base Chinese
 * <code>Calendar</code> utilities
 * 
 * @see Date
 * @see SimpleDateFormat
 * @see Calendar
 * @see GregorianCalendar
 * @see TimeZone
 * @see Matcher
 * @see Pattern
 * @author shellpo shih
 * @version 1.0
 */
public final class ChineseCalendarUtil {

	/* ==================Utilities=================== */
	/**
	 * Constructor
	 * 
	 * @param date
	 */
	public ChineseCalendarUtil(Date date) {
		if (date == null)
			date = new Date();
		this.init(date.getTime());
	}

	public ChineseCalendarUtil(long timeMillis) {
		this.init(timeMillis);
	}

	/**
	 * Return {@code String} the animal
	 * 
	 * @return {@code String} the animal
	 */
	public String animal() {
		return Animals[(this.lunarYear - 4) % 12];
	}

	/**
	 * Return {@code String} the Chinese term
	 * 
	 * @return {@code String} the Chinese term
	 */
	public String term() {
		if (getSolarTermDay(solarYear, solarMonth * 2) == solarDay) {
			return solarTerm[solarMonth * 2];
		} else if (getSolarTermDay(solarYear, solarMonth * 2 + 1) == solarDay) {
			return solarTerm[solarMonth * 2 + 1];
		}
		return "";
	}

	/**
	 * Return {@code String} the lunar GanZhi String
	 * 
	 * @return {@code String} the lunar GanZhi String
	 */
	public String chineseEra() {
		return this.chineseEraYear() + "年" + this.chineseEraMonth() + "月" + this.chineseEraDay() + "日";
	}

	/**
	 * Return {@code String} the lunar year string
	 * 
	 * @return {@code String} the lunar year string
	 */
	public String chineseEraYear() {
		return stringTianganAndDizhi(this.cyclicalYear);
	}

	/**
	 * Return {@code String} the lunar month string
	 * 
	 * @return {@code String} the lunar month string
	 */
	public String chineseEraMonth() {
		return stringTianganAndDizhi(this.cyclicalMonth);
	}

	/**
	 * Return {@code String} the lunar day string
	 * 
	 * @return {@code String} the lunar day string
	 */
	public String chineseEraDay() {
		return stringTianganAndDizhi(this.cyclicalDay);
	}

	/**
	 * Return {@code String} the lunar date string
	 * 
	 * @return {@code String} the lunar date string
	 */
	public String lunar() {
		return this.lunarYear() + "年" + this.lunarMonth() + "月" + this.lunarDay() + "日";
	}

	/**
	 * Return {@code String} the lunar year string
	 * 
	 * @return {@code String} the lunar year string
	 */
	public String lunarYear() {
		return stringLunarYear(this.lunarYear);
	}

	/**
	 * Return {@code String} the lunar month string
	 * 
	 * @return {@code String} the lunar month string
	 */
	public String lunarMonth() {
		return (this.isLeap() ? "闰" : "") + stringLunarMonth(this.lunarMonth);
	}

	/**
	 * Return {@code String} the lunar day string
	 * 
	 * @return {@code String} the lunar day string
	 */
	public String lunarDay() {
		return stringLunarDay(this.lunarDay);
	}

	/**
	 * Return {@code String} the week string
	 * 
	 * @return {@code String} the week string
	 */
	public String week() {
		return Weeks[getDayOfWeek() - 1];
	}

	/**
	 * Return {@code String} the Chinese conflicts string
	 * 
	 * @return the Chinese conflicts string
	 */
	public String animalConflict() {
		return Animals[getDizhiDay()] + "日冲" + Animals[(getDizhiDay() + 6) % 12] + "生年";
	}

	/* ===================Private Method=================== */
	/**
	 * initialization
	 * 
	 * @param timeMillis
	 */
	private void init(long timeMillis) {
		this.solar = Calendar.getInstance();
		this.solar.setTimeInMillis(timeMillis);
		Calendar baseDate = new GregorianCalendar(1900, 0, 31);
		long offset = (timeMillis - baseDate.getTimeInMillis()) / 86400000;
		this.lunarYear = 1900;
		int daysInLunarYear = lunarYearDaysNumber(this.lunarYear);
		while (this.lunarYear < 2100 && offset >= daysInLunarYear) {
			offset -= daysInLunarYear;
			daysInLunarYear = lunarYearDaysNumber(++this.lunarYear);
		}
		int lunarMonth = 1;
		int leapMonth = lunarLeapMonth(this.lunarYear);
		this.isLeapYear = leapMonth > 0;
		boolean leapDec = false;
		boolean isLeap = false;
		int daysInLunarMonth = 0;
		while (lunarMonth < 13 && offset > 0) {
			if (isLeap && leapDec) {
				daysInLunarMonth = lunarLeapDays(this.lunarYear);
				leapDec = false;
			} else {
				daysInLunarMonth = lunarMonthDays(this.lunarYear, lunarMonth);
			}
			if (offset < daysInLunarMonth) {
				break;
			}
			offset -= daysInLunarMonth;
			if (leapMonth == lunarMonth && isLeap == false) {
				leapDec = true;
				isLeap = true;
			} else {
				lunarMonth++;
			}
		}
		this.maxDayInMonth = daysInLunarMonth;
		this.lunarMonth = lunarMonth;
		this.isLeap = (lunarMonth == leapMonth && isLeap);
		this.lunarDay = (int) offset + 1;
		this.getCyclicalData();
	}

	private void getCyclicalData() {
		this.solarYear = this.solar.get(Calendar.YEAR);
		this.solarMonth = this.solar.get(Calendar.MONTH);
		this.solarDay = this.solar.get(Calendar.DAY_OF_MONTH);
		int cyclicalYear = 0;
		int cyclicalMonth = 0;
		int cyclicalDay = 0;
		int term2 = getSolarTermDay(solarYear, 2);
		if (solarMonth < 1 || (solarMonth == 1 && solarDay < term2)) {
			cyclicalYear = (solarYear - 1900 + 36 - 1) % 60;
		} else {
			cyclicalYear = (solarYear - 1900 + 36) % 60;
		}
		int firstNode = getSolarTermDay(solarYear, solarMonth * 2);
		if (solarDay < firstNode) {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 12) % 60;
		} else {
			cyclicalMonth = ((solarYear - 1900) * 12 + solarMonth + 13) % 60;
		}
		cyclicalDay = (int) (UTC(solarYear, solarMonth, solarDay, 0, 0, 0) / 86400000 + 25567 + 10) % 60;
		this.cyclicalYear = cyclicalYear;
		this.cyclicalMonth = cyclicalMonth;
		this.cyclicalDay = cyclicalDay;
	}

	/**
	 * Return {@code Integer} the leap month
	 * 
	 * @param lunarYear
	 *            The Lunar Year
	 * @return {@code Integer} the leap month
	 */
	private static int lunarLeapMonth(int lunarYear) {
		int leapMonth = LunarInfo[lunarYear - 1900] & 0xf;
		leapMonth = (leapMonth == 0xf ? 0 : leapMonth);
		return leapMonth;
	}

	/**
	 * Return {@code int} the days of the leap month
	 * 
	 * @param lunarYear
	 *            The Lunar Year
	 * @return the days of the leap month
	 */
	private static int lunarLeapDays(int lunarYear) {
		return lunarLeapMonth(lunarYear) > 0 ? ((LunarInfo[lunarYear - 1899] & 0xf) == 0xf ? 30 : 29) : 0;
	}

	/**
	 * Return {@code int} days number in lunar year
	 * <p>
	 * at least two 346 days in lunar year
	 * 
	 * @param lunarYear
	 *            The Lunar Year
	 * @return {@code Integer}days number in lunar year
	 */
	private static int lunarYearDaysNumber(int lunarYear) {
		int daysInLunarYear = 348;
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			daysInLunarYear += ((LunarInfo[lunarYear - 1900] & i) != 0) ? 1 : 0;
		}
		daysInLunarYear += lunarLeapDays(lunarYear);
		return daysInLunarYear;
	}

	/**
	 * Return {@code Integer} the days number in lunar year and month
	 * 
	 * @param lunarYear
	 *            The Lunar Year
	 * @param lunarMonth
	 *            The Lunar Month
	 * @return {@code Integer} the days number in lunar year and month
	 */
	private static int lunarMonthDays(int lunarYear, int lunarMonth) {
		return ((LunarInfo[lunarYear - 1900] & (0x10000 >> lunarMonth)) != 0) ? 30 : 29;
	}

	/**
	 * Return {@code Integer} the index in solar term day
	 * 
	 * @param solarYear
	 *            The solar Year
	 * @param index
	 *            The term index(begin from 0(小寒))
	 * @return {@code Integer} the index in solar term day
	 */
	private static int getSolarTermDay(int solarYear, int index) {
		long l = (long) 31556925974.7 * (solarYear - 1900) + SolarTermInfo[index] * 60000L;
		l = l + UTC(1900, 0, 6, 2, 5, 0);
		return getUTCDay(new Date(l));
	}

	/**
	 * Return {@code Integer} the global UTC time
	 * 
	 * @param date
	 * @return the global UTC time
	 */
	private static GregorianCalendar utcCal = null;

	private static synchronized int getUTCDay(Date date) {
		makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.setTimeInMillis(date.getTime());
			return utcCal.get(Calendar.DAY_OF_MONTH);
		}
	}

	private static synchronized void makeUTCCalendar() {
		if (utcCal == null) {
			utcCal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		}
	}

	/**
	 * Return {@code Long} the millisecond from 1970.1.1 to the define date
	 * 
	 * @param y
	 * @param m
	 * @param d
	 * @param h
	 * @param min
	 * @param sec
	 * @return the millisecond from 1970.1.1 to the define date
	 */
	private static synchronized long UTC(int y, int m, int d, int h, int min, int sec) {
		makeUTCCalendar();
		synchronized (utcCal) {
			utcCal.clear();
			utcCal.set(y, m, d, h, min, sec);
			return utcCal.getTimeInMillis();
		}
	}

	/**
	 * Return {@code String} the TianGan and DiZhi Content
	 * 
	 * @param cyclicalNumber
	 *            the index (0:甲子)
	 * @return {@code String} the TianGan and DiZhi Content
	 */
	private static String stringTianganAndDizhi(int cyclicalNumber) {
		return TianGan[numberTiangan(cyclicalNumber)] + DeZhi[numberDizhi(cyclicalNumber)];
	}

	/**
	 * Return {@code String} the TianGan Content
	 * 
	 * @param cyclicalNumber
	 *            the index (0:甲)
	 * @return {@code String} the TianGan Content
	 */
	private static int numberTiangan(int cyclicalNumber) {
		return cyclicalNumber % 10;
	}

	/**
	 * Return {@code String} the DiZhi Content
	 * 
	 * @param cyclicalNumber
	 *            the index (0:子)
	 * @return {@code String} the DiZhi Content
	 */
	private static int numberDizhi(int cyclicalNumber) {
		return cyclicalNumber % 12;
	}

	/**
	 * Return {@code String} the lunar year string
	 * 
	 * @param lunarYear
	 *            the lunar year
	 * @return {@code String} the lunar year string
	 */
	private static String stringLunarYear(int lunarYear) {
		return stringTianganAndDizhi(lunarYear - 1900 + 36);
	}

	/**
	 * Return {@code String} the lunar month string
	 * 
	 * @param lunarMonth
	 *            the lunar month
	 * @return {@code String} the lunar month string
	 */
	private static String stringLunarMonth(int lunarMonth) {
		String lunarMonthString = "";
		if (lunarMonth == 1) {
			lunarMonthString = ChineseNumber[4];
		} else {
			if (lunarMonth > 9)
				lunarMonthString += ChineseNumber[1];
			if (lunarMonth % 10 > 0)
				lunarMonthString += LunarNumber[lunarMonth % 10];
		}
		return lunarMonthString;
	}

	/**
	 * Return {@code String} the lunar day string
	 * 
	 * @param lunarDay
	 *            the lunar day
	 * @return {@code String} the lunar day string
	 */
	private static String stringLunarDay(int lunarDay) {
		if (lunarDay < 1 || lunarDay > 30)
			return "";
		int i1 = lunarDay / 10;
		int i2 = lunarDay % 10;
		String c1 = ChineseNumber[i1];
		String c2 = LunarNumber[i2];
		if (lunarDay < 11)
			c1 = ChineseNumber[0];
		if (i2 == 0)
			c2 = ChineseNumber[1];
		return c1 + c2;
	}

	/**
	 * Return {@code int} parse string to int
	 * 
	 * @param str
	 *            the number string
	 * @return {@code int} number
	 */
	private static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Matcher the festival
	 * 
	 * @see sFtv
	 * @see lFtv
	 * @see wFtv
	 */
	private final static Pattern FestivalPattern = Pattern.compile("^(\\d{2})(\\d{2})([\\s\\*])(.+)$");
	private final static Pattern OtherPattert = Pattern.compile("^(\\d{2})(\\d)(\\d)([\\s\\*])(.+)$");

	private synchronized void findFestival() {
		int sM = this.getSolarMonth();
		int sD = this.getSolarDay();
		int lM = this.getLunarMonth();
		int lD = this.getLunarDay();
		int sy = this.getSolarYear();
		Matcher m;
		for (int i = 0; i < Festival.length; i++) {
			m = FestivalPattern.matcher(Festival[i]);
			if (m.find()) {
				if (sM == parseInt(m.group(1)) && sD == parseInt(m.group(2))) {
					this.isSFestival = true;
					this.sFestivalName = m.group(4);
					if ("*".equals(m.group(3)))
						this.isHoliday = true;
					break;
				}
			}
		}
		for (int i = 0; i < ChineseFestival.length; i++) {
			m = FestivalPattern.matcher(ChineseFestival[i]);
			if (m.find()) {
				if (lM == parseInt(m.group(1)) && lD == parseInt(m.group(2))) {
					this.isLFestival = true;
					this.lFestivalName = m.group(4);
					if ("*".equals(m.group(3)))
						this.isHoliday = true;
					break;
				}
			}
		}

		int w, d;
		for (int i = 0; i < OtherFestival.length; i++) {
			m = OtherPattert.matcher(OtherFestival[i]);
			if (m.find()) {
				if (this.getSolarMonth() == parseInt(m.group(1))) {
					w = parseInt(m.group(2));
					d = parseInt(m.group(3));
					if (this.solar.get(Calendar.WEEK_OF_MONTH) == w && this.solar.get(Calendar.DAY_OF_WEEK) == d) {
						this.isSFestival = true;
						this.sFestivalName += "|" + m.group(5);
						if ("*".equals(m.group(4)))
							this.isHoliday = true;
					}
				}
			}
		}
		if (sy > 1874 && sy < 1909)
			this.description = "光绪" + (((sy - 1874) == 1) ? "元" : "" + (sy - 1874));
		if (sy > 1908 && sy < 1912)
			this.description = "宣统" + (((sy - 1908) == 1) ? "元" : String.valueOf(sy - 1908));
		if (sy > 1911 && sy < 1950)
			this.description = "民国" + (((sy - 1911) == 1) ? "元" : String.valueOf(sy - 1911));
		if (sy > 1949)
			this.description = "共和国" + (((sy - 1949) == 1) ? "元" : String.valueOf(sy - 1949));
		this.description += "年";
		this.sFestivalName = this.sFestivalName.replaceFirst("^\\|", "");
		this.isFinded = true;
	}

	/* ===========Base Properties=================== */
	private boolean isFinded = false;
	private boolean isSFestival = false;
	private boolean isLFestival = false;
	private String sFestivalName = "";
	private String lFestivalName = "";
	private String description = "";
	private boolean isHoliday = false;

	private Calendar solar;
	private int lunarYear;
	private int lunarMonth;
	private int lunarDay;
	private boolean isLeap;
	private boolean isLeapYear;
	private int solarYear;
	private int solarMonth;
	private int solarDay;
	private int cyclicalYear = 0;
	private int cyclicalMonth = 0;
	private int cyclicalDay = 0;
	private int maxDayInMonth = 29;
	/* ==================Initialize Properties================ */
	private final static int[] LunarInfo = { 0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0,
			0x55d2, 0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd295, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977, 0x497f, 0xa4b0,
			0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970, 0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf,
			0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f, 0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2,
			0xa950, 0xb557, 0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0, 0xaea6,
			0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0, 0x96d0, 0x4dd5, 0x4ad0, 0xa4d0,
			0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6, 0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40,
			0xaf46, 0xab60, 0x9570, 0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0,
			0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5, 0xa950, 0xb4a0, 0xbaa4,
			0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930, 0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6,
			0xa4e0, 0xd260, 0xea65, 0xd530, 0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520,
			0xdd45, 0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0, 0x4b63, 0x937f,
			0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef, 0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0,
			0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4, 0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4,
			0xa5b0, 0x52b0, 0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260, 0xe968,
			0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252, 0xd520 };
	private final static int[] SolarTermInfo = { 0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551,
			218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532,
			504758 };
	private final static String[] TianGan = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
	private final static String[] DeZhi = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
	private final static String[] Animals = { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
	private final static String[] solarTerm = { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
			"小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
	private final static String[] LunarNumber = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	private final static String[] ChineseNumber = { "初", "十", "廿", "卅", "正", "腊", "冬", "闰" };
	private final static String[] Festival = { "0101*元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0315 消费者权益日", "0401 愚人节",
			"0501*劳动节", "0504 青年节", "0509 郝维节", "0512 护士节", "0601 儿童节", "0701 建党节 香港回归纪念", "0801 建军节", "0808 父亲节",
			"0816 燕衔泥节", "0909 毛泽东逝世纪念", "0910 教师节", "0928 孔子诞辰", "1001*国庆节", "1006 老人节", "1024 联合国日", "1111 光棍节",
			"1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1225 圣诞节", "1226 毛泽东诞辰纪念" };
	private final static String[] ChineseFestival = { "0101*春节、弥勒佛诞", "0106 定光佛诞", "0115 元宵节", "0208 释迦牟尼佛出家",
			"0215 释迦牟尼佛涅槃", "0209 海空上师诞", "0219 观世音菩萨诞", "0221 普贤菩萨诞", "0316 准提菩萨诞", "0404 文殊菩萨诞", "0408 释迦牟尼佛诞",
			"0415 佛吉祥日——释迦牟尼佛诞生、成道、涅槃三期同一庆(即南传佛教国家的卫塞节)", "0505*端午节", "0513 伽蓝菩萨诞", "0603 护法韦驮尊天菩萨诞",
			"0619 观世音菩萨成道——此日放生、念佛，功德殊胜", "0707 七夕情人节", "0713 大势至菩萨诞", "0715 中元节", "0724 龙树菩萨诞", "0730 地藏菩萨诞",
			"0815*中秋节", "0822 燃灯佛诞", "0909 重阳节", "0919 观世音菩萨出家纪念日", "0930 药师琉璃光如来诞", "1005 达摩祖师诞", "1107 阿弥陀佛诞",
			"1208 释迦如来成道日，腊八节", "1224 小年", "1229 华严菩萨诞", "0100*除夕" };
	private final static String[] OtherFestival = { "0520 母亲节", "0716 合作节", "0730 被奴役国家周" };
	private final static String[] Weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/* ===========Getter and Setter=============== */

	public int getTianganYear() {
		return numberTiangan(this.cyclicalYear);
	}

	public int getTianganMonth() {
		return numberTiangan(this.cyclicalMonth);
	}

	public int getTianganDay() {
		return numberTiangan(this.cyclicalDay);
	}

	public int getDizhiYear() {
		return numberDizhi(this.cyclicalYear);
	}

	public int getDizhiMonth() {
		return numberDizhi(this.cyclicalMonth);
	}

	public int getDizhiDay() {
		return numberDizhi(this.cyclicalDay);
	}

	public boolean isLeap() {
		return isLeap;
	}

	public boolean isLeapYear() {
		return isLeapYear;
	}

	public boolean isBigMonth() {
		return this.getMaxDayInMonth() > 29;
	}

	public int getMaxDayInMonth() {
		return this.maxDayInMonth;
	}

	public int getLunarDay() {
		return lunarDay;
	}

	public int getLunarMonth() {
		return lunarMonth;
	}

	public int getLunarYear() {
		return lunarYear;
	}

	public int getSolarDay() {
		return solarDay;
	}

	public int getSolarMonth() {
		return solarMonth + 1;
	}

	public int getSolarYear() {
		return solarYear;
	}

	public int getDayOfWeek() {
		return this.solar.get(Calendar.DAY_OF_WEEK);
	}

	public boolean isBlackFriday() {
		return (this.getSolarDay() == 13 && this.solar.get(Calendar.DAY_OF_WEEK) == 6);
	}

	public boolean isToday() {
		Calendar clr = Calendar.getInstance();
		return clr.get(Calendar.YEAR) == this.solarYear && clr.get(Calendar.MONTH) == this.solarMonth
				&& clr.get(Calendar.DAY_OF_MONTH) == this.solarDay;
	}

	public String getSFestivalName() {
		return this.sFestivalName;
	}

	public String getLFestivalName() {
		return this.lFestivalName;
	}

	public boolean isLFestival() {
		if (!this.isFinded)
			this.findFestival();
		return this.isLFestival;
	}

	public boolean isSFestival() {
		if (!this.isFinded)
			this.findFestival();
		return this.isSFestival;
	}

	public boolean isFestival() {
		return this.isSFestival() || this.isLFestival();
	}

	public boolean isHoliday() {
		if (!this.isFinded)
			this.findFestival();
		return this.isHoliday;
	}

	public String getDescription() {
		if (!this.isFinded)
			this.findFestival();
		return this.description;
	}

}