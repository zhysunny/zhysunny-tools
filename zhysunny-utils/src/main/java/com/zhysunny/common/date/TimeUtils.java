package com.zhysunny.common.date;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间工具类
 * @author 章云
 * @date 2019/7/27 15:04
 */
public class TimeUtils {

    private TimeUtils() {}

    private static final int HOUR = 24;
    private static final int MINUTE = 60;
    private static final int SECOND = 60;

    /**
     * 1秒间隔
     * @return
     */
    public static List<String> getSecondList() {
        return getSecondList(1);
    }

    /**
     * period秒间隔
     * @param period
     * @return
     */
    public static List<String> getSecondList(int period) {
        int size = 86400 / period + 1;
        List<String> list = new ArrayList<>(size);
        List<String> secondList = new ArrayList<>(86400);
        StringBuilder time;
        StringBuilder hour;
        StringBuilder minute;
        StringBuilder second;
        for (int i = 0; i < HOUR; i++) {
            hour = new StringBuilder(2);
            hour.append(i);
            if (hour.length() == 1) {
                hour.insert(0, '0');
            }
            for (int j = 0; j < MINUTE; j++) {
                minute = new StringBuilder(2);
                minute.append(j);
                if (minute.length() == 1) {
                    minute.insert(0, '0');
                }
                for (int k = 0; k < SECOND; k++) {
                    second = new StringBuilder(2);
                    second.append(k);
                    if (second.length() == 1) {
                        second.insert(0, '0');
                    }
                    time = new StringBuilder(6);
                    time.append(hour).append(minute).append(second);
                    secondList.add(time.toString());
                }
            }
        }
        for (int i = 0; i < secondList.size(); i += period) {
            list.add(secondList.get(i));
        }
        list.add("000000");
        return list;
    }

    public static void main(String[] args) {
        System.out.println(TimeUtils.getSecondList());
        System.out.println(TimeUtils.getSecondList(10));
    }

}
