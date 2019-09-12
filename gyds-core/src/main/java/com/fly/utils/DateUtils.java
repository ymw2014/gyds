package com.fly.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理
 */
public class DateUtils {
	private final static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 时间格式(yyyy-MM-dd)
	 */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/**
	 * 时间格式(yyyy-MM-dd HH:mm:ss)
	 */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}
	public static Date parse(String date) {
		return parse(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	public static Date parse(String date, String pattern) {
		if (date != null&&date!="") {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try {
				return df.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 计算距离现在多久，非精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBefore(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		} else if (hour > 0) {
			r += hour + "小时";
		} else if (min > 0) {
			r += min + "分";
		} else if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}

	/**
	 * 计算距离现在多久，精确
	 *
	 * @param date
	 * @return
	 */
	public static String getTimeBeforeAccurate(Date date) {
		Date now = new Date();
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String r = "";
		if (day > 0) {
			r += day + "天";
		}
		if (hour > 0) {
			r += hour + "小时";
		}
		if (min > 0) {
			r += min + "分";
		}
		if (s > 0) {
			r += s + "秒";
		}
		r += "前";
		return r;
	}
	/*
	 * 计算开始结束相差时间
	 */
	public static int  longOfTwoDate(Date first,Date second) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(first);
		int cnt = 0;
		while(calendar.getTime().compareTo(second)!=0){
			calendar.add(Calendar.DATE, 1);
			cnt++;
		}
		return cnt;
	}
	/**
     * 
     * @param date 当前时间
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     * @return
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour*60*60*1000 + minute*60*1000 + second*1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis()-millisecond);
         
        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis()+23*60*60*1000 + 59*60*1000 + 59*1000);
        }
        return cal.getTime();
    }
    /**
     * 获取前月的第一天 获取前月的最后一天
     * @return
     */
    public static Map<String, Object> firstLastDay() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String firstDay,lastDay;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//获取前月的第一天
    	Calendar cal_1=Calendar.getInstance();//获取当前日期
    	cal_1.add(Calendar.MONTH, -1);
    	cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
    	firstDay = format.format(cal_1.getTime());
    	map.put("starteTime",firstDay);
    	//获取前月的最后一天
    	Calendar cale = Calendar.getInstance();
    	cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
    	lastDay = format.format(cale.getTime());
    	map.put("endTime", lastDay);
    	return map;
    }
    /**
     * 获取本月的第一天 获取本月的最后一天
     * @return
     */
    public static Map<String, Object> firstLastDay1() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String firstDay,lastDay;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//获取前月的第一天
    	Calendar cal_1=Calendar.getInstance();//获取当前日期
    	cal_1.add(Calendar.MONTH, 0);
    	cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
    	firstDay = format.format(cal_1.getTime());
    	map.put("starteTime",firstDay);
    	//获取前月的最后一天
    	Calendar cale = Calendar.getInstance();
    	lastDay = format.format(cale.getTime());
    	map.put("endTime", lastDay);
    	return map;
    }
    /**
     * 获取去年的第一天 获取去年的最后一天
     * @return
     */
    public static Map<String, Object> firstLastYearDay() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String firstDay,lastDay;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//获取前年的第一天
    	Calendar cal_1=Calendar.getInstance();//获取当前日期
    	cal_1.add(Calendar.YEAR, -1);
    	cal_1.set(Calendar.DAY_OF_YEAR,1);//设置为1号,当前日期既为本月第一天
    	firstDay = format.format(cal_1.getTime());
    	map.put("starteTime",firstDay);
    	//获取前年的最后一天
    	Calendar cale = Calendar.getInstance();
    	cale.set(Calendar.DAY_OF_YEAR,0);
    	lastDay = format.format(cale.getTime());
    	map.put("endTime", lastDay);
    	return map;
    }
    /**
     * 获取今年的第一天 获取今年的最后一天
     * @return
     */
    public static Map<String, Object> firstLastYearDay1() {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String firstDay,lastDay;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	//获取前年的第一天
    	Calendar cal_1=Calendar.getInstance();//获取当前日期
    	cal_1.add(Calendar.YEAR, 0);
    	cal_1.set(Calendar.DAY_OF_YEAR,1);//设置为1号,当前日期既为本月第一天
    	firstDay = format.format(cal_1.getTime());
    	map.put("starteTime",firstDay);
    	//获取前年的最后一天
    	Calendar cale = Calendar.getInstance();
    	lastDay = format.format(cale.getTime());
    	map.put("endTime", lastDay);
    	return map;
    }
    

}