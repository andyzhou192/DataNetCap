package com.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS"); //yyyy/MM/dd HH:mm:ss.SSS

	public static Date parse(String date_string) throws ParseException {
		return sdf.parse(date_string);
	}

	public static String format(Date date, String format_string) {
		sdf.applyPattern(format_string);
		return sdf.format(date);
	}

	public static String format(Date date) {
		return sdf.format(date);
	}
	
	/**
	 * 获取默认时区
	 * @return 显示sun.util.calendar.ZoneInfo[id="GMT",offset=0,dstSavings=0,useDaylight=false,transitions=0,lastRule=null]
	 */
	public static TimeZone getDefaultTimeZone(){
		return TimeZone.getDefault();
	}

}
