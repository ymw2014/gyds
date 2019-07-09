package com.fly.wx.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：WangYanke
 * @date ：Created in 2019/3/12 17:40
 * @description：Wang Yanke
 * @modified By：
 * @version: 1.1.0$
 */
public class DateUtil {

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
