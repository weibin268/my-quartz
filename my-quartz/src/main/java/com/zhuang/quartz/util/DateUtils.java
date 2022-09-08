package com.zhuang.quartz.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

}
