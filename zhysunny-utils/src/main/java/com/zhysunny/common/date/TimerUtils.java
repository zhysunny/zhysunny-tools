package com.zhysunny.common.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定点定时任务
 * @author 章云
 * @date 2019/7/27 15:01
 */
public class TimerUtils {

    private TimerUtils() {}

    private static ScheduledThreadPoolExecutor executors = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
        private AtomicInteger count = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            int c = count.incrementAndGet();
            Thread t = new Thread(r);
            t.setName("Timer-" + c);
            return t;
        }
    });

    /**
     * 设置定点时间与运行时间的间隔
     * @param executeTime
     * @param task
     * @param period
     */
    public static void taskTimer(String executeTime, TimerTask task, long period) {
        String[] time = executeTime.split("\\:");
        Calendar c = Calendar.getInstance();
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        int second = Integer.parseInt(time[2]);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        Date date = c.getTime();
        if (date.before(new Date())) {
            date = addDay(date, 1);
        }
        long initialDelay = date.getTime() - System.currentTimeMillis();
        executors.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * 默认时间间隔一天
     * @param executeTime
     * @param task
     */
    public static void taskTimer(String executeTime, TimerTask task) {
        int period = 24 * 60 * 60 * 1000;
        taskTimer(executeTime, task, period);
    }

    /**
     * 设定固定分钟时间
     * @param executeTime
     * @param task
     * @param period
     */
    public static void taskTimer(int executeTime, TimerTask task, long period) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, executeTime);
        c.set(Calendar.SECOND, 0);
        Date date = c.getTime();
        if (date.before(new Date())) {
            date = addHour(date, 1);
        }
        long initialDelay = date.getTime() - System.currentTimeMillis();
        executors.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    /**
     * 默认时间间隔一小时
     * @param executeTime
     * @param task
     */
    public static void taskTimer(int executeTime, TimerTask task) {
        int period = 1 * 60 * 60 * 1000;
        taskTimer(executeTime, task, period);
    }

    /**
     * 加一天
     * @param date
     * @param num
     * @return
     */
    private static Date addDay(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTime();
    }

    /**
     * 加一小时
     * @param date
     * @param num
     * @return
     */
    private static Date addHour(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, num);
        return c.getTime();
    }
}
