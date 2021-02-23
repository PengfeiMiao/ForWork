package com.mpf.website.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * Java8 时间处理
 * @author: BuZheng
 * @date: 2020-01-16 下午 3:25 
 *
 */
public class DateUtil {

	/**
	 * locaDate判断两个时间是否相等
	 *
	 * @param startTime     时间1
	 * @param endTime       时间2
	 * @return: boolean
	 * @author: BuZheng
	 **/
	public  static boolean  localDateEquals(LocalDate startTime, LocalDate endTime){
		if(startTime.equals(endTime)){
			return true;
		}
		return false;
	}

	/**
	 * locaDateTime判断两个时间是否相等
	 *
	 * @param startTime     时间1
	 * @param endTime       时间2
	 * @return: boolean
	 * @author: BuZheng
	 **/
	public static boolean  locaDateTimeEquals(LocalDateTime startTime, LocalDateTime endTime){
		if(startTime.equals(endTime)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是生日  ( Date )
	 *
	 * @param birthday      生日
	 * @param referDay      参照时间
	 * @return: boolean
	 * @author: BuZheng
	 **/
	public static  boolean isBirthdayByDate(Date birthday, Date referDay){
		LocalDate localDateBirthday = date2LocalDate(birthday);
		LocalDate localDateReferDay = date2LocalDate(referDay);
		//生日时间 x月x日
		MonthDay birthdayTime = MonthDay.of(localDateBirthday.getMonth(), localDateBirthday.getDayOfMonth());
		//参照时间 x月x日
		MonthDay referMonthDay = MonthDay.from(localDateReferDay);
		if(birthdayTime.equals(referMonthDay)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是生日  ( LocalDate )
	 *
	 * @param birthday      生日
	 * @param referDay      参照时间
	 * @return: boolean
	 * @author: BuZheng
	 **/
	public static  boolean isBirthdayByLocalDate(LocalDate birthday, LocalDate referDay){
		//生日时间 x月x日
		MonthDay birthdayTime = MonthDay.of(birthday.getMonth(), birthday.getDayOfMonth());
		//参照时间 x月x日
		MonthDay referMonthDay = MonthDay.from(referDay);
		if(birthdayTime.equals(referMonthDay)){
			return true;
		}
		return false;
	}

	/**
	 * 时间转换（LocalDateTime ==》 Date）
	 * @param nowDayStart   当前时间
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date localDateTime2Date(LocalDateTime nowDayStart) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = nowDayStart.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	/**
	 * 时间转换（LocalDate ==》 Date）
	 *
	 * @param localDate
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date localDate2Date(LocalDate localDate) {
		if(null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return Date.from(zonedDateTime.toInstant());
	}

	/**
	 * 时间转换（Date ==》 LocalDateTime）
	 *
	 * @param date      时间
	 * @return: java.time.LocalDateTime
	 * @author: BuZheng
	 **/
	public static LocalDateTime date2LocalDateTime(Date date){
		//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
		Instant instant = date.toInstant();
		//A time-zone ID, such as {@code Europe/Paris}.(时区)
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

		return localDateTime;
	}

	/**
	 * 时间转换（Date ==》 LocalDate）
	 *
	 * @param date      时间
	 * @return: java.time.LocalDateTime
	 * @author: BuZheng
	 **/
	public static LocalDate date2LocalDate(Date date){
		//An instantaneous point on the time-line.(时间线上的一个瞬时点。)
		Instant instant = date.toInstant();
		//A time-zone ID, such as {@code Europe/Paris}.(时区)
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();

		return localDate;
	}

	/**
	 * 时间增加
	 *
	 * @param date      开始时间
	 * @param num      数量
	 * @param temporalUnit  以什么形式相加（ ChronoUnit.WEEKS/DAYS）
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date datePlus(Date date, int num, TemporalUnit temporalUnit){
		LocalDateTime localDateTime = date2LocalDateTime(date);
		return localDateTime2Date(localDateTime.plus(num, temporalUnit));
	}

	/**
	 * 时间减少
	 *
	 * @param date      开始时间
	 * @param num      数量
	 * @param temporalUnit  以什么形式减少（ ChronoUnit.WEEKS/DAYS）
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date dateMinus(Date date, int num, TemporalUnit temporalUnit){
		LocalDateTime localDateTime = date2LocalDateTime(date);
		return localDateTime2Date(localDateTime.minus(num, temporalUnit));
	}

	/**
	 * 获取当前时间戳
	 * @author: BuZheng
	 */
	public static long nowMillis(){
		return Clock.systemUTC().millis();
	}

	/**
	 * 获取昨天日期
	 * @param toDay      日期
	 * @return: java.util.Date
	 * @author: BuZheng
	 */
	public static Date getYesterday(Date toDay){

		return localDateTime2Date(date2LocalDateTime(toDay).minusDays(1));
	}


	/**
	 * 判断是否是闰年
	 * @param time      日期
	 * @return: boolean
	 * @author: BuZheng
	 **/
	public static boolean isLeapYear(Date time){
		return date2LocalDate(time).isLeapYear();
	}

	/**
	 * 获取相差的时间（通过Period获取年月日）
	 *
	 * @param startDate
	 * @param endDate
	 * @return: java.time.Period
	 * @author: BuZheng
	 **/
	public static Period getDiffer(Date startDate, Date endDate){
		return Period.between(date2LocalDate(endDate), date2LocalDate(startDate));
	}

	/**
	 * 字符串转成日期（年-月-日）
	 *  字符串格式必须与需转格式一致！
	 * @param timeStr      日期字符串
	 * @param pattern      需转换的日期格式
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date string2Date(String timeStr, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate parse = LocalDate.parse(timeStr, dateTimeFormatter);
		return  localDate2Date(parse);
	}

	/**
	 * 字符串转成日期（年-月-日 时-分-秒）
	 *  字符串格式必须与需转格式一致！
	 * @param timeStr      日期字符串
	 * @param pattern      需转换的日期格式
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date string2DateTime(String timeStr, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime parse = LocalDateTime.parse(timeStr, dateTimeFormatter);
		return  localDateTime2Date(parse);
	}

	/**
	 * 日期转字符串（年-月-日）
	 *  字符串格式必须与需转格式一致！
	 *
	 * @param date      日期
	 * @param pattern      需转换的日期格式
	 * @return: java.lang.String
	 * @author: BuZheng
	 **/
	public static String date2String(Date date, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		String format = date2LocalDate(date).format(dateTimeFormatter);
		return  format;
	}

	/**
	 * 日期转字符串（年-月-日 时-分-秒）
	 *  字符串格式必须与需转格式一致！
	 *
	 * @param date      日期
	 * @param pattern      需转换的日期格式
	 * @return: java.lang.String
	 * @author: BuZheng
	 **/
	public static String dateTime2String(Date date, String pattern){
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		String format = date2LocalDateTime(date).format(dateTimeFormatter);
		return  format;
	}

	/**
	 * 获取传入日期最小时间
	 *
	 * @param date      日期
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date getDateMin(Date date){
		LocalDateTime localDateTime = date2LocalDate(date).atTime(LocalTime.MIN);
		return localDateTime2Date(localDateTime);
	}

	/**
	 * 获取传入日期最大时间
	 *
	 * @param date      日期
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public static Date getDateMax(Date date){
		LocalDateTime localDateTime = date2LocalDate(date).atTime(LocalTime.MAX);
		return localDateTime2Date(localDateTime);
	}



	/**
	 * 时间戳转时间（Date）
	 *
	 * @param time  时间
	 * @return: java.util.Date
	 * @author: BuZheng
	 **/
	public  static Date timestamp2Date(String time){
		Instant timestamp = Instant.ofEpochMilli(Long.parseLong(time));
		ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
		LocalDateTime localDateTime = losAngelesTime.toLocalDateTime();
		return localDateTime2Date(localDateTime);
	}

	/**
	 * 两个时间戳相差几天
	 *
	 * @param startTime     开始时间
	 * @param endTime       结束时间
	 * @return: int
	 * @author: BuZheng
	 **/
	public static long differDays(Long startTime, Long endTime){
		//获取相差时间戳
		long abs = Math.abs(endTime - startTime);
		long days = abs / (3600 * 24 * 1000);
		return days;
	}


}