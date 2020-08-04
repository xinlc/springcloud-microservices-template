package com.example.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    /**
     * 参数的字符串使用指定的格式转换日期型的方法
     * @param str 转换的对象
     * @param pattern 日期时间格式的模式
     * @return 参数null的时候返回null，字符串时按照指定的日期时间格式实行Date型转换
     */
    public static Date convertString2Date(String str, String pattern) {
        if (str == null || "".equals(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(str.trim()));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return calendar.getTime();
    }

    /**
     * 日期(yyyy/mm/dd hh:mm:ss)的变换
     * @param date 日期对象
     * @return yyyy-mm-dd hh:mm:ss"
     */
    public static String convertDate2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     *
     * @param str
     * @param pattern
     * @return
     */
    public static String convertStr2Str(String str, String pattern){
        if (str == null || "".equals(str)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(str.trim()));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        return sdf.format(calendar.getTime());
    }

    public static void main(String[] arg) throws ParseException {
        System.out.println("日期输出："+convertStr2Str("2019-08-20 19:20:08","yyyy-MM-dd"));
    }

    /**
     * 获取精确到秒的时间戳
     * @param format
     * @return
     */
    public static int timeToStamp(String format,Date time) {
        if (null == format) {
            return 0;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        String date;
        if (time != null){
            date = df.format(time);
        } else {
            date = df.format(new Date());
        }
        String timestamp = null;
        try {
            timestamp = String.valueOf(df.parse(date).getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.valueOf(timestamp);

    }



    /**
     * 获取昨天的日期
     * @return
     */
    public static Date getYesterDay(){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return d;
    }

    /**
     * 确认时间是否为昨天
     * @param oneTime
     * @return true：是，false：不是
     */
    public static boolean isYesterday(String oneTime){
        if(oneTime == null){
            return  false;
        }
        //获得昨天的值
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String twoTime = sdf.format(calendar.getTime());
        //比较
        return oneTime.equals(twoTime);
    }



    /**
     * 确认时间是否为今天
     * @param oneTime
     * @return true：是，false：不是
     */
    public static boolean isToday(Date oneTime) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(oneTime);
        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        int year2 = c2.get(Calendar.YEAR);
        int month2 = c2.get(Calendar.MONTH)+1;
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if(year1 == year2 && month1 == month2 && day1 == day2){
            return true;
        }
        return false;
    }

    /**
     * 返回秒数
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int dateDiff(Date startDate, Date endDate){
        long date = endDate.getTime() - startDate.getTime();
        return (int) (date/1000);
    }

    /**
     * 取得今天的开始时间
     * @return
     */
    public static Date getTodayStartTime(){
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 取得今天的结束时间
     * @return
     */
    public static Date getTodayEndTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
}
