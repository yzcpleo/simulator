package com.shhxzq.fin.simulator.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author kangyonggan
 * @since 2016/11/30
 */
public class DateUtils {

    public static final String MAIL_DATE_MD_FORMAT = "MM月dd日";
    public static final String MAIL_DATE_HM_FORMAT = "HH时mm分";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String SHORT_DATE_GBK_FORMAT = "yyyy年MM月dd日";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分";
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String LONG_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String MAIL_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String MAIL_DATE_HHMM_FORMAT = "HH:mm";
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String FULL_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";
    public static final String FULL_DATE_COMPACT_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String LDAP_DATE_FORMAT = "yyyyMMddHHmm'Z'";
    public static final String US_LOCALE_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String MAIL_DATE_DT_PART_FORMAT = "yyyyMMdd";
    public static final String MAIL_TIME_TM_PART_FORMAT = "HHmmss";
    public static final String LONG_DATE_TM_PART_FORMAT = "HH:mm:ss";
    public static final String Long_DATE_TM_PART_GBK_FORMAT = "HH时mm分ss秒";
    public static final String MAIL_DATA_DTM_FORMAT = "yyyy年MM月dd日HH:mm";
    public static final String MAIL_DATA_DTM_PART_FORMAT = "MM月dd日HH:mm";
    public static final String POINT_DATA_DTM_PART_FORMAT = "yyyy.MM.dd";
    public static final String DEFAULT_DATE_FORMAT = US_LOCALE_DATE_FORMAT;
    public static long NANO_ONE_SECOND = 1000;
    public static long NANO_ONE_MINUTE = 60 * NANO_ONE_SECOND;
    public static long NANO_ONE_HOUR = 60 * NANO_ONE_MINUTE;
    public static long NANO_ONE_DAY = 24 * NANO_ONE_HOUR;

    /**
     * 计算指定日期加减天数后的日期
     *
     * @param inputDate  '20080301'
     * @param dateFormat 日期格式，'yyyyMMdd' 参考SimpleDateFormat
     * @param amount     数量
     */
    public static String addDay(String inputDate, String dateFormat, int amount) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date = sdf.parse(inputDate);
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, amount);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算指定日期加减月数后的日期
     *
     * @param inputDate  '20080301'
     * @param dateFormat 日期格式，'yyyyMMdd' 参考SimpleDateFormat
     * @param amount     数量
     */
    public static String addMonth(String inputDate, String dateFormat, int amount) {

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date = sdf.parse(inputDate);
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, amount);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算指定日期加减年数后的日期
     *
     * @param inputDate  '20080301'
     * @param dateFormat 日期格式，'yyyyMMdd' 参考SimpleDateFormat
     * @param amount     数量
     */
    public static String addYear(String inputDate, String dateFormat, int amount) {

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date date = sdf.parse(inputDate);
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, amount);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期 yyyyMMdd
     */
    public static String getCurrentDate() {
        return toMailDateDtPartString(getNow());
    }

    /**
     * 获取当前日期 HHmmss
     */
    public static String getCurrentTime() {
        return toMailDateDtTimePartString(getNow());
    }

    /**
     * 获取当期时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return toMailDateString(getNow());
    }

    /**
     * 获取当期时间yyyyMMddHHmmssSSS
     *
     * @return
     */
    public static String getCurrentFullDateTime() {
        return toFullDateCompactString(getNow());
    }

    /**
     * 获取当前日期类型时间
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 将一个日期型转换为'yyyyMMddHHmmss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateString(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATE_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyyMMdd'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateDtPartString(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATE_DT_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为'HHmmss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateDtTimePartString(Date aDate) {
        return toFormatDateString(aDate, MAIL_TIME_TM_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param aDate
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date aDate, String formatStr) {
        if (aDate == null)
            return StringUtils.EMPTY;
        return new SimpleDateFormat(formatStr).format(aDate);
    }

    /**
     * 将一个日期型转换为'yyyyMMddHHmmssSSS'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toFullDateCompactString(Date aDate) {
        return toFormatDateString(aDate, FULL_DATE_COMPACT_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy-MM-dd HH:mm:ss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLongDateString(Date aDate) {
        return toFormatDateString(aDate, LONG_DATE_FORMAT);
    }

    /**
     * 将一个符合'yyyy-MM-dd HH:mm:ss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseLongDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, LONG_DATE_FORMAT);
    }

    /**
     * 将一个符合'yyyy-MM-dd'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseShortDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, SHORT_DATE_FORMAT);
    }

    /**
     * 将一个符合指定格式的字串解析成日期型
     *
     * @param aDateStr
     * @param formatter
     * @return
     * @throws ParseException
     */
    public static final Date parser(String aDateStr, String formatter) throws ParseException {
        if (StringUtils.isBlank(aDateStr)) return null;
        if (StringUtils.isBlank(formatter)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.parse(aDateStr);
    }
}