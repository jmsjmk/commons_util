/**
 * Project Name:common_util
 * File Name:DateUtil.java
 * Package Name:com.wearetheteam.util
 * Date:2014年12月11日上午10:20:21
 *
 */

package com.wearetheteam.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ClassName:DateUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014年12月11日 上午10:20:21 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class DateUtil {

	/**
	 * 将ISO8859_1编码的字符串转化为GB2312编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param ISO8859_1str
	 *          通过ISO8859_1编码的字符串
	 * @return 通过GB2312编码的字符串
	 */
	public static String GB2312FromISO8859_1(String ISO8859_1str) {
		if (ISO8859_1str == null) {
			return "";
		} else {
			try {
				return new String(ISO8859_1str.getBytes("ISO8859_1"), "GB2312");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将UTF编码的字符串转化为GB2312编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param UTF
	 *          通过UTF编码的字符串
	 * @return 通过GB2312编码的字符串
	 */
	public static String GB2312FromUTF(String UTF) {
		if (UTF == null) {
			return "";
		} else {
			try {
				return new String(UTF.getBytes("UTF-8"), "GB2312");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String GBKFromISO8859_1(String ISO8859_1) {
		if (ISO8859_1 == null) {
			return "";
		} else {
			try {
				return new String(ISO8859_1.getBytes("ISO8859_1"), "GBK");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String GBKFromUTF(String UTF) {
		if (UTF == null) {
			return "";
		} else {
			try {
				return new String(UTF.getBytes("UTF-8"), "GBK");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String get8BitString(String strDate) {
		if (strDate == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyyMMdd");
		Date d = null;
		try {
			d = sdf.parse(strDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return sdf2.format(d);
	}

	public static String get8ByteTo10Byte(String strDate) {
		if (strDate == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(strDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return sdf2.format(d);
	}

	/**
	 * 获取当前的日期yyyyMMdd
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public static String getCurrentDateTime() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd H:mm");
		return sdf.format(new Date());
	}

	/**
	 * 获得当前天
	 *
	 * @return 当前天
	 */
	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得当前月份
	 *
	 * @return 当前月份
	 */
	public static int getCurrentMonth() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M");
		return Integer.parseInt(sdf.format(new java.util.Date()));
	}

	/**
	 * 获取当前的时间yyyyMMddHHmmss
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	// static public String mm_dd_yyyy = "MM-dd-yyyy HH:mm:ss";
	/**
	 * 获得当前年份
	 *
	 * @return 当前年份，格式如：2003
	 */
	public static int getCurrentYear() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
		return Integer.parseInt(sdf.format(new java.util.Date()));
	}

	public static String getHourMinute(java.util.Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("H:mm");
		return sdf.format(date);
	}

	public static String getMonthDay(java.util.Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("M月d日");
		return sdf.format(date);
	}

	/**
	 * 获得形如 19770608 格式的当前年月日
	 *
	 * @return 当前年月日
	 */
	public static String getSimpleCurrentDate() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return sdf.format(new java.util.Date());
	}

	public static String getStandedDateTime(String strDate) {
		if (strDate == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(strDate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return sdf2.format(d);
	}

	/**
	 * 获取前一天日期yyyyMMdd
	 *
	 * @see 经测试，针对闰年02月份或跨年等情况，该代码仍有效。测试代码如下
	 * @see calendar.set(Calendar.YEAR, 2013);
	 * @see calendar.set(Calendar.MONTH, 0);
	 * @see calendar.set(Calendar.DATE, 1);
	 * @see 测试时，将其放到<code>calendar.add(Calendar.DATE, -1);</code>前面即可
	 * @return 返回的日期格式为yyyyMMdd
	 */
	public static String getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	}

	/**
	 * 判断字符串是否符合日期格式
	 *
	 * @param str
	 *          字符串时间
	 * @return
	 */
	public static boolean isDate(String strDate) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(strDate);
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 判断是否是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String strNumber) {
		boolean bolResult = false;
		try {
			Double.parseDouble(strNumber);
			bolResult = true;
		} catch (NumberFormatException ex) {
			bolResult = false;
		}
		return bolResult;
	}

	public static String ISO8859_1FromGBK(String GBK) {
		if (GBK == null) {
			return "";
		} else {
			try {
				return new String(GBK.getBytes("GBK"), "ISO8859_1");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将UTF-8编码的字符串转化为ISO8859_1编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param UTF
	 *          通过UTF编码的字符串
	 * @return 通过ISO8859_1编码的字符串
	 */
	public static String ISO8859_1FromUTF(String UTFstr) {
		if (UTFstr == null) {
			return "";
		} else {
			try {
				return new String(UTFstr.getBytes("UTF-8"), "ISO8859_1");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将GB2312编码的字符串转化为ISO8859_1编码的字符串
	 *
	 * @param GBstr
	 *          GB2312编码的字符串
	 * @return ISO8859_1编码的字符串
	 */
	public static String ISO8859_1String(String GBstr) {
		if (GBstr == null) {
			return "";
		} else {
			try {
				return new String(GBstr.getBytes("GB2312"), "ISO8859_1");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static void main(String[] argv) {
		System.out.println(getMonthDay(new java.util.Date()));
		System.out.println(getHourMinute(new java.util.Date()));

	}

	/**
	 * 字符串替换，将 source 中的 oldString 全部换成 newString
	 *
	 * @param source
	 *          源字符串
	 * @param oldString
	 *          老的字符串
	 * @param newString
	 *          新的字符串
	 * @return 替换后的字符串
	 */
	private static String Replace(String source, String oldString, String newString) {
		StringBuffer output = new StringBuffer();

		int lengthOfSource = source.length(); // 源字符串长度
		int lengthOfOld = oldString.length(); // 老字符串长度

		int posStart = 0; // 开始搜索位置
		int pos; // 搜索到老字符串的位置

		while ((pos = source.indexOf(oldString, posStart)) >= 0) {
			output.append(source.substring(posStart, pos));

			output.append(newString);
			posStart = pos + lengthOfOld;
		}

		if (posStart < lengthOfSource) {
			output.append(source.substring(posStart));
		}

		return output.toString();
	}

	public static String subOneDay(String strDate) {
		// YYYY-MM-DD
		int[] standardDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int[] leapyearDays = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int y = Integer.parseInt(strDate.substring(0, 4));
		int m = Integer.parseInt(strDate.substring(4, 6));
		int d = Integer.parseInt(strDate.substring(6, 8)) - 1;
		int maxDateCount = 0;

		System.out.println(y);
		System.out.println(m);
		System.out.println(d);

		if ((((y % 4) == 0) && ((y % 100) != 0)) || ((y % 400) == 0)) {
			maxDateCount = leapyearDays[m - 1];
		} else {
			maxDateCount = standardDays[m - 1];
		}

		if (d > maxDateCount) {
			d = 1;
			m++;
		}

		if (m > 12) {
			m = 1;
			y++;
		}
		java.text.DecimalFormat yf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat mdf = new java.text.DecimalFormat("00");
		return yf.format(y) + mdf.format(m) + mdf.format(d);
	}

	/**
	 * 转换SQL中的特殊符号，比如将单引号"'"替换为"''"，以免产生SQLException
	 *
	 * @param sqlstr
	 *          原SQL
	 * @return 处理后的SQL
	 */
	public static String toSql(String sqlstr) {
		String strsql = sqlstr;
		if (strsql == null) {
			return "";
		}
		strsql = Replace(strsql, "'", "''");
		return strsql;
	}

	/**
	 * 去除字符串两端空格。
	 *
	 * @param str
	 *          需要处理的字符串
	 * @return 去掉了两端空格的字符串，如果str 为 null 则返回 ""
	 */
	public static String trim(String str) {
		if (str != null) {
			return str.trim();
		} else {
			return "";
		}
	}

	/**
	 * 将GB2312编码的字符串转化为UTF-8编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param GB2312
	 *          通过GB2312编码的字符串
	 * @return 通过UTF-8编码的字符串
	 */
	public static String UTFFromGB2312(String GB2312) {
		if (GB2312 == null) {
			return "";
		} else {
			try {
				return new String(GB2312.getBytes("GB2312"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static String UTFFromGBK(String GBK) {
		if (GBK == null) {
			return "";
		} else {
			try {
				return new String(GBK.getBytes("GBK"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将ISO8859_1编码的字符串转化为UTF-8编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param ISO8859_1str
	 *          通过ISO8859_1编码的字符串
	 * @return 通过UTF-8编码的字符串
	 */
	public static String UTFFromISO8859_1(String ISO8859_1str) {
		return ISO8859_1str;
	}

	public String addOneDay(String strDate) // YYYY-MM-DD
	{
		int[] standardDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int[] leapyearDays = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int y = Integer.parseInt(strDate.substring(0, 4));
		int m = Integer.parseInt(strDate.substring(4, 6));
		int d = Integer.parseInt(strDate.substring(6, 8)) + 1;
		int maxDateCount = 0;

		System.out.println(y);
		System.out.println(m);
		System.out.println(d);

		if ((((y % 4) == 0) && ((y % 100) != 0)) || ((y % 400) == 0)) {
			maxDateCount = leapyearDays[m - 1];
		} else {
			maxDateCount = standardDays[m - 1];
		}

		if (d > maxDateCount) {
			d = 1;
			m++;
		}

		if (m > 12) {
			m = 1;
			y++;
		}
		java.text.DecimalFormat yf = new java.text.DecimalFormat("0000");
		java.text.DecimalFormat mdf = new java.text.DecimalFormat("00");
		return yf.format(y) + mdf.format(m) + mdf.format(d);
	}

	/**
	 * 计算两个时间
	 *
	 * @param str
	 *          原时间，stradd，需增加的时间
	 * @return 计算后的时间
	 */
	public String addTime(String str, String stradd) {
		String rsTime = "";
		int hour = 0;
		int sec = 0;
		int secadd = 0;
		int houradd = 0;
		str = trim(str);
		stradd = trim(stradd);
		if (str.length() == 5) {
			hour = Integer.parseInt(str.substring(0, 2));

			sec = Integer.parseInt(str.substring(3, 5));

		} else if (str.length() == 4) {
			hour = Integer.parseInt(str.substring(0, 1));

			sec = Integer.parseInt(str.substring(2, 4));

		}
		if (trim(stradd).length() == 5) {

			secadd = Integer.parseInt(stradd.substring(0, 2));

		} else if (trim(stradd).length() == 4) {
			secadd = Integer.parseInt(stradd.substring(0, 1));

		} else if (trim(stradd).length() == 7) {
			houradd = Integer.parseInt(stradd.substring(0, 1));
			secadd = Integer.parseInt(stradd.substring(2, 4));
		}
		int a = sec + secadd;
		if (a < 60) {
			String stra = Integer.toString(a);
			String strh = Integer.toString(hour + houradd);
			if (stra.length() == 1) {
				stra = "0" + stra;
			}
			if (strh.length() == 1) {
				strh = "0" + strh;
			} else if (Integer.parseInt(strh) > 24) {
				int h = Integer.parseInt(strh) / 24;
				strh = Integer.toString(h);
				if (h < 10) {
					strh = "0" + Integer.toString(h);
				}
			}
			rsTime = strh + ":" + stra;

		} else if (a == 60) {
			String strh = Integer.toString(hour + houradd + 1);
			if (strh.length() == 1) {
				strh = "0" + strh;
			} else if (Integer.parseInt(strh) > 24) {
				int h = Integer.parseInt(strh) / 24;
				strh = Integer.toString(h);
				if (h < 10) {
					strh = "0" + Integer.toString(h);
				}
			}
			rsTime = strh + ":00";

		} else if (a > 60) {
			int i = a / 60;
			int j = a % 60;
			String strj = Integer.toString(j);

			if (strj.length() == 1) {
				strj = "0" + strj;
			}
			String strh = Integer.toString(hour + houradd + i);
			if (strh.length() == 1) {
				strh = "0" + strh;
			} else if (Integer.parseInt(strh) > 24) {
				int h = Integer.parseInt(strh) / 24;
				strh = Integer.toString(h);
				if (h < 10) {
					strh = "0" + Integer.toString(h);
				}
			}
			rsTime = strh + ":" + strj;

			if (j == 0) {
				rsTime = strh + ":00";

			}

		}
		return rsTime;
	}

	public String dateadd(Date strDate, int a) {
		String str = "";

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String strDate1 = sdf.format(strDate);

		int year = Integer.parseInt(strDate1.substring(0, 4));
		int month = Integer.parseInt(strDate1.substring(5, 7));
		int day = Integer.parseInt(strDate1.substring(8, 10));
		int md = getDayForMonth(month);
		int i = (day + a) / md;
		int j = (day + a) % md;
		if (j == 0) {
			i = i - 1;
			j = md;
		}
		String strmon = "";
		String strday = "";
		String mondiff = "";
		if (i < 2) {
			if (Integer.toString(j).length() == 1) {
				strday = "0" + Integer.toString(j);
			} else {
				strday = Integer.toString(j);
			}
			if ((month + i) > 12) {
				int yeardiff = (month + i) / 12;
				int monthmod = (month + i) % 12;
				mondiff = Integer.toString(monthmod);
				if (Integer.toString(monthmod).length() == 1) {
					mondiff = "0" + Integer.toString(monthmod);
				}
				str = Integer.toString(year + yeardiff) + "-" + mondiff + "-" + strday;
			} else {
				strmon = Integer.toString(month + i);
				if (Integer.toString(month + i).length() == 1) {
					strmon = "0" + Integer.toString(month + i);
				}

				str = Integer.toString(year) + "-" + strmon + "-" + strday;

			}
		} else {
			// 主要判断假如天数，月份溢出的处理，
		}
		return str;
	}

	public String dateformat(String str) {
		String day = str.substring(0, 2);
		String month = str.substring(3, 5);
		String year = str.substring(6, 10);
		str = year + "-" + month + "-" + day;
		return str;
	}

	/**
	 * 返回两个日期相差天数
	 *
	 * @param d1
	 *          日期
	 * @param d2
	 *          日期
	 * @return 天数
	 */
	public int diffDate(Date d1, Date d2) {
		if ((d1 == null) || (d2 == null)) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();

		// from Locale, has nothing to do with your input date format
		int zoneoffset = cal.get(Calendar.ZONE_OFFSET);
		int dstoffset = cal.get(Calendar.DST_OFFSET);

		// getTime() return absolute GMT time
		// compensate with the offsets
		long dl1 = d1.getTime() + zoneoffset + dstoffset;
		long dl2 = d2.getTime() + zoneoffset + dstoffset;

		int intDaysFirst = (int) (dl1 / (60 * 60 * 1000 * 24)); // 60*60*1000
		int intDaysSecond = (int) (dl2 / (60 * 60 * 1000 * 24));

		return intDaysFirst > intDaysSecond ? intDaysFirst - intDaysSecond : intDaysSecond - intDaysFirst;
	}

	/**
	 * 计算两个时间相差的分钟数
	 *
	 * @param time1
	 *          string，time2，string
	 * @return string
	 */
	public String diffTime(String time1, String time2) {
		String rsTime = "";
		int hour = 0;
		int hour2 = 0;
		int sec = 0;
		int sec2 = 0;
		String str1 = trim(time1);
		String str2 = trim(time2);
		if (str1.length() == 5) {
			hour = Integer.parseInt(str1.substring(0, 2));

			sec = Integer.parseInt(str1.substring(3, 5));

		} else if (str1.length() == 4) {
			hour = Integer.parseInt(str1.substring(0, 1));

			sec = Integer.parseInt(str1.substring(2, 4));

		}
		if (str2.length() == 5) {
			hour2 = Integer.parseInt(str2.substring(0, 2));

			sec2 = Integer.parseInt(str2.substring(3, 5));

		} else if (str2.length() == 4) {
			hour2 = Integer.parseInt(str2.substring(0, 1));

			sec2 = Integer.parseInt(str2.substring(2, 4));

		}

		// int a = sec + secsub;
		if (sec < sec2) {
			// System.out.println("sub <");
			String jstr = Integer.toString((sec + 60) - sec2);
			if (jstr.length() == 1) {
				jstr = "0" + jstr;
			}
			if ((hour - 1) != hour2) {

				String hstr = Integer.toString(hour - 1 - hour2);

				if (hstr.length() == 1) {
					hstr = "0" + hstr;
				}
				rsTime = hstr + ":" + jstr + ":00";
			} else {
				rsTime = jstr + ":00";
			}
		} else if (sec == sec2) {
			// System.out.println("sub =");
			if (hour != hour2) {

				String strh = Integer.toString(hour - hour2);
				// System.out.println("strh="+strh);
				if (strh.length() == 1) {
					strh = "0" + strh;
				}
				rsTime = strh + ":00" + ":00";
			} else {
				rsTime = "00:00";
			}
		} else if (sec > sec2) {
			// System.out.println("sub >");
			String jstr = Integer.toString(sec - sec2);
			// System.out.println("jstr="+jstr);
			if (jstr.length() == 1) {
				jstr = "0" + jstr;
			}
			if (hour != hour2) {
				String hstr = Integer.toString(hour - hour2);
				// System.out.println("hstr="+hstr);
				if (hstr.length() == 1) {
					hstr = "0" + hstr;
				}
				rsTime = hstr + ":" + jstr + ":00";
			} else {
				rsTime = jstr + ":00";
			}
		}
		return rsTime;
	}

	/**
	 * 将ISO8859_1编码的字符串转化为GB2312编码的字符串，主要用来处理中文显示乱码的问题
	 *
	 * @param ISO8859_1str
	 *          通过ISO8859_1编码的字符串
	 * @return 通过GB2312编码的字符串
	 */
	public String GBString(String ISO8859_1str) {
		if (ISO8859_1str == null) {
			return "";
		} else {
			try {
				return new String(ISO8859_1str.getBytes("ISO8859_1"), "GB2312");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 将给定的时间转换为格式是8位的字符串
	 *
	 * @param date
	 *          给定的时间
	 * @return 格式化后的字符串形式的时间
	 */
	public String get8BitDate(java.util.Date date) {
		if (date == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public int getDayForMonth(int month) {
		int a = 0;
		switch (month) {
		case 1:
			a = 31;
			break;
		case 2:
			a = 28;
			break;
		case 3:
			a = 31;
			break;
		case 4:
			a = 30;
			break;
		case 5:
			a = 31;
			break;
		case 6:
			a = 30;
			break;
		case 7:
			a = 31;
			break;
		case 8:
			a = 31;
			break;
		case 9:
			a = 30;
			break;
		case 10:
			a = 31;
			break;
		case 11:
			a = 30;
			break;
		case 12:
			a = 31;
			break;
		default:

		}
		return a;
	}

	/**
	 * 将GB2312编码的字符串转化为ISO8859_1编码的字符串
	 *
	 * @param GBstr
	 *          GB2312编码的字符串
	 * @return ISO8859_1编码的字符串
	 */
	public String ISO8859_1FromGB2312(String GBstr) {
		if (GBstr == null) {
			return "";
		} else {
			try {
				return new String(GBstr.getBytes("GB2312"), "ISO8859_1");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public String replace(String str, String strOld, String strNew) {
		String r = str;
		if ((str != null) && (strOld != null) && (strNew != null)) {
			int idx = str.indexOf(strOld);
			if (idx != -1) {
				String strPre = "";
				r = "";
				String strSuf = str;
				for (; idx != -1; idx = strSuf.indexOf(strOld)) {
					strPre = strSuf.substring(0, idx);
					strSuf = strSuf.substring(idx + strOld.length());
					r = r + strPre + strNew;
				}

				r = r + strSuf;
			}
		}
		return r;
	}

	/**
	 * 计算两个时间
	 *
	 * @param str
	 *          原时间，strsub，需减少的时间
	 * @return 计算后的时间
	 */
	public String subTime(String str, String strSub) {
		String rsTime = "";
		int hour = 0;
		int sec = 0;
		int secsub = 0;
		str = trim(str);
		strSub = trim(strSub);
		if (str.length() == 5) {
			hour = Integer.parseInt(str.substring(0, 2));

			sec = Integer.parseInt(str.substring(3, 5));

		} else if (str.length() == 4) {
			hour = Integer.parseInt(str.substring(0, 1));

			sec = Integer.parseInt(str.substring(2, 4));

		}
		if (trim(strSub).length() == 5) {
			secsub = Integer.parseInt(strSub.substring(0, 2));

		} else if (trim(strSub).length() == 4) {
			secsub = Integer.parseInt(strSub.substring(0, 1));

		}

		// int a = sec + secsub;
		if (sec < secsub) {
			// System.out.println("sub <");
			String jstr = Integer.toString((sec + 60) - secsub);
			String hstr = Integer.toString(hour - 1);
			// System.out.println("jstr="+jstr);
			// System.out.println("hstr="+hstr);
			if (jstr.length() == 1) {
				jstr = "0" + jstr;
			}
			if (hstr.length() == 1) {
				hstr = "0" + hstr;
			}
			rsTime = hstr + ":" + jstr;

		} else if (sec == secsub) {
			// System.out.println("sub =");
			String strh = Integer.toString(hour);
			// System.out.println("strh="+strh);
			if (strh.length() == 1) {
				strh = "0" + strh;
			}
			rsTime = strh + ":00";

		} else if (sec > secsub) {
			// System.out.println("sub >");
			String jstr = Integer.toString(sec - secsub);
			// System.out.println("jstr="+jstr);
			String hstr = Integer.toString(hour);
			// System.out.println("hstr="+hstr);
			if (jstr.length() == 1) {
				jstr = "0" + jstr;
			}
			if (hstr.length() == 1) {
				hstr = "0" + hstr;
			}
			rsTime = hstr + ":" + jstr;

		}
		return rsTime;
	}

	public String tenformat(String str) {
		if (str.length() == 8) {
			String year = str.substring(0, 4);
			String month = "0" + str.substring(5, 6);
			String day = "0" + str.substring(7, 8);
			str = year + "-" + month + "-" + day;
		} else if (str.length() == 9) {
			if (str.substring(6, 7).equals("-")) {
				str = str.substring(0, 5) + "0" + str.substring(5, 9);
			} else {
				str = str.substring(0, 8) + "0" + str.substring(8, 9);
			}

		}
		return str;
	}

	public String to_date(String strdate, String df) {
		if (strdate == null) {
			return "";
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat("M/d/yyyy H:m:s");
		Date d = null;
		try {
			d = sdf1.parse(strdate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return sdf.format(d);
	}

	public String toSENDstr(String input) {
		String r = input;
		r = replace(r, "&", "");
		r = replace(r, "/", "|");
		r = replace(r, "/r", "");
		r = replace(r, "/n", "");
		r = replace(r, "'", "");
		r = replace(r, " ", "");
		return r;
	}

}
