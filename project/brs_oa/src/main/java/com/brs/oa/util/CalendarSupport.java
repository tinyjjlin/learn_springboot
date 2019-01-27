package com.brs.oa.util;

import org.apache.http.util.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tiny lin
 * @date 2019/1/12
 */
public class CalendarSupport {
    public final static int SUNDAY_ID = 1;
    public final static int SATURDAY_ID =7;


    /**
     * 获取字符串格式的日期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDateStr(int year,int month,int day){
        String dateStr = year+"-";
        if(month>0 && month<10){
            dateStr = dateStr+"0"+month;
        }else{
            dateStr = dateStr+month;
        }
        dateStr = dateStr+"-";
       if(day>0 && day<10 ){
           dateStr =dateStr+"0"+day;
       }else{
           dateStr= dateStr+day;
       }
       return dateStr;
    }

    /**
     * 获取字符串日期的年，月
     * @param dateStr
     * @return
     */
    public static int[] getYearAndMonth(String dateStr){
        int result[] = new int[2];

        String[] dateArr = dateStr.split("-");
        if(dateArr !=null && dateArr.length == 2){
            result[0] =Integer.parseInt(dateArr[0]);
            if(dateArr[1].contains("0")){
                dateArr[1].replace("0","" ).trim();
            }
            result[1]=Integer.parseInt(dateArr[1]);
        }


        return result;
    }
    /**
     * 获取日期最大天数
     * @param date
     * @return
     */
    public static int getMaxDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DATE);
    }



    public static int getMaxDayByDate(int year,int  month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month-1 );
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 判断输入日期是否在当前日期前面
     * @param date
     * @return
     */
    public static boolean isBeforeNow(Date date){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 =Calendar.getInstance();
        calendar1.setTime(date);
        return calendar1.before(calendar);
    }

    public static boolean isBeforeNow(int year,int month ){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month-1 );

        Calendar currentCalendar =Calendar.getInstance();
        return calendar.before(currentCalendar);
    }

    public static boolean isAfterNow(int year,int month ){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month-1 );

        Calendar currentCalendar =Calendar.getInstance();
        return calendar.after(currentCalendar);
    }



    /**
     * 判断输入的日期是否为休息日（未排除节假日，调休工作日）
     * @param date
     * @return
     */
    public static boolean isRestDay(Date date ){
        int weekId = getDayOfWeek(date);
        if(weekId == SATURDAY_ID || weekId == SUNDAY_ID){
            return true;
        }
        return false;
    }

    /**
     * 获取日期对应的星期几；
     * 1 星期日，2星期一，3星期二，4星期三，5星期四，6星期五，7星期六
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        if(date == null){
            calendar.setTime(new Date(System.currentTimeMillis()));
        }else{
            calendar.setTime(date);
        }

        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    /**
     * 将String格式的日期转换为Date格式
     * @param dateTimeStr
     * @return
     */
    public static Date str2Date(String dateTimeStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            if(!TextUtils.isEmpty(dateTimeStr)){
                date = simpleDateFormat.parse(dateTimeStr);
            }

        }catch(ParseException e){
            date = null;
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 将String格式的日期转换为Date格式
     * @param dateStr
     * @return
     */
    public static Date strToDate(String dateStr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if(!TextUtils.isEmpty(dateStr)){
                date = simpleDateFormat.parse(dateStr);
            }

        }catch(ParseException e){
            date = null;
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 两个时间比较大小
     * @param time1
     * @param time2
     * @return
     */
    public static int timeCompare(String time1,String time2){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        try{
            Date date1 = dateFormat.parse(time1);
            Date date2 = dateFormat.parse(time2);

            System.out.println(date1.getTime()-date2.getTime());

            if(date1.getTime()>date2.getTime()){
                return 1;
            }else if(date1.getTime() == date2.getTime()){
                return 0;
            }else{
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  -2;
    }

    /**
     * 两个时间值比较
     * @param timeL1
     * @param timeL2
     * @return
     */
    public static int timeCompare(long timeL1,long timeL2){
        if(timeL1>timeL2){
            return 1;
        }else if(timeL1==timeL2){
            return 0;
        }
           return -1;
    }
    /**
     * 比指定时间早的时间值
     * @param currentTimeL
     * @param minute
     * @return
     */
    public static long getBeforeTime(long currentTimeL,int minute){
         long minuteL = minute*60000;
         return currentTimeL - minuteL;
    }

    /**
     * 比指定时间迟的时间值
     * @param currentTimeL
     * @param minute
     * @return
     */
    public static long getAfterTime(long currentTimeL,int minute){
        long minuteL = minute*60000;
        return currentTimeL + minuteL;
    }

    /**
     * "2019-01-14 13:48:53"
     * @param timeStr
     * @return
     */
    public static long getTimeValue(String timeStr){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date;
        Long timeL= null;
        try { date = dateFormat.parse(timeStr);
             timeL= date.getTime();
        }catch (Exception e){
            e.printStackTrace();
        }
        return timeL;
    }




    /**
     * 将日期转换成字符串格式
     * @param date
     * @return
     */
    public static String dateToStr(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);

        return dateStr;
    }

    public static String dateToTimeStr(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr = simpleDateFormat.format(date);

        return dateStr;
    }
    public static void  main(String []args){

//        String dateStr = "2019-01-12";
//        System.out.println(CalendarSupport.getDayOfWeek(CalendarSupport.strToDate("")));
//        System.out.println(CalendarSupport.isBeforeNow(2018,12));
//        System.out.println(CalendarSupport.getMaxDayByDate(CalendarSupport.strToDate("2019-01-12")));
//        System.out.println(CalendarSupport.getMaxDayByDate(2019,1));
//
//        System.out.println(CalendarSupport.getYearAndMonth("2019-02")[1]);
//
//        System.out.println(CalendarSupport.dateToTimeStr(CalendarSupport.str2Date("2019-01-14 13:48:53")));

        System.out.println(CalendarSupport.timeCompare("08:30:00","08:30:00" ));
        System.out.println(CalendarSupport.timeCompare("08:35:00","08:30:00" ));
        System.out.println(CalendarSupport.timeCompare("13:30:45","13:30:49" ));
       System.out.println(CalendarSupport.getTimeValue("07:35:00"));

    }


}
