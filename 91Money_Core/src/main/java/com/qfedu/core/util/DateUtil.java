package com.qfedu.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *@Author feri
 *@Date Created in 2018/7/24 23:02
 */
public class DateUtil {

    /**
     * 用来计算发送两次验证码之间的间隔 ()
     * @param d1
     * @param d2
     * @return
     */
    public static long getBetweenSecond(Date d1 , Date d2 ){
        return  Math.abs((d1.getTime() - d2.getTime())/1000) ;
    }

    /**
     * 得到一天的最后一秒钟
     *
     */
    public static Date endOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    public static String getDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
