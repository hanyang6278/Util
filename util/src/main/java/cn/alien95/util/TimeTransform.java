package cn.alien95.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by linlongxin on 2016/1/17.
 * 时间戳类
 */
public class TimeTransform {

    private final int DAY_OF_MILLISECONDS = 1000 * 60 * 60 * 24;
    private final int HOUR_OF_MILLISECONDS = 1000 * 60 * 60;
    private final int MINUTES_OF_MILLISECONDS = 1000 * 60;

    private static TimeTransform instance;

    private TimeTransform() {
    }

    public static TimeTransform getInstance() {
        if (instance == null) {  //外面这层判断用来提高速度的
            synchronized (TimeTransform.class) {
                if (instance == null) {
                    instance = new TimeTransform();
                }
            }
        }
        return instance;
    }

    /**
     * 按照格式转换成对应日期
     *
     * @param milliseconds 时间戳毫秒为单位
     * @param format       yyyy-MM-dd  HH:mm:ss 类似的日期
     * @return
     */
    public String transformFormat(long milliseconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date(milliseconds);
        return formatter.format(date);
    }

    /**
     * 时间转换成星期几
     *
     * @param milliseconds 时间戳，单位毫秒
     * @return 返回星期几
     */
    public int transformDayOfWeek(long milliseconds) {
        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.DAY_OF_WEEK;
    }

    /**
     * 时间转换成对应日期 如：1月7日（周四）
     *
     * @param milliseconds
     * @return
     */
    public String transformDateAndDayOfWeek(long milliseconds) {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentDate);

        Date date = new Date(milliseconds);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat formatter;

        if (currentCalendar.YEAR == calendar.YEAR) {
            formatter = new SimpleDateFormat("MM月dd日");
            return formatter.format(calendar) + "(周" + calendar.DAY_OF_WEEK + ")";
        } else {
            formatter = new SimpleDateFormat("yyyy年MM月dd日");
            return formatter.format(calendar);
        }
    }

    /**
     * 把时间戳转换成最近的时间
     *
     * @param milliseconds
     * @return
     */
    public String transformRecentDate(long milliseconds) {
        long currentMilliseconds = System.currentTimeMillis();
        long timeDistance = currentMilliseconds - milliseconds;
        if (timeDistance < 1000) {
            return "刚刚";
        } else if (timeDistance > 0) {  //过去的时间
            if (timeDistance / 1000 < 60) {
                return timeDistance / 1000 + "秒前";
            }
            if (timeDistance / MINUTES_OF_MILLISECONDS < 60) {
                return timeDistance / MINUTES_OF_MILLISECONDS + "分钟前";
            } else {
                if (timeDistance / HOUR_OF_MILLISECONDS < 24) {
                    return timeDistance / HOUR_OF_MILLISECONDS + "小时前";
                } else {
                    if (timeDistance / DAY_OF_MILLISECONDS < 30) {
                        return timeDistance / DAY_OF_MILLISECONDS + "天前";
                    }
                }
            }

        } else {
            timeDistance = timeDistance * -1;
            if (timeDistance / 1000 < 60) {
                return timeDistance / 1000 + "秒后";
            }
            if (timeDistance / MINUTES_OF_MILLISECONDS < 60) {
                return timeDistance / MINUTES_OF_MILLISECONDS + "分钟后";
            } else {
                if (timeDistance / HOUR_OF_MILLISECONDS < 24) {
                    return timeDistance / HOUR_OF_MILLISECONDS + "小时后";
                } else {
                    if (timeDistance / DAY_OF_MILLISECONDS < 30) {
                        return timeDistance / DAY_OF_MILLISECONDS + "天后";
                    }
                }
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(new Date(milliseconds));
    }

}
