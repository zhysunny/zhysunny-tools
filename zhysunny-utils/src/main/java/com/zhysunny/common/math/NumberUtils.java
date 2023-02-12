package com.zhysunny.common.math;

/**
 * @author zhysunny
 * @date 2020/11/8 20:05
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
    /**
     * 奇数
     * @param number
     * @return
     */
    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }

    /**
     * 偶数
     * @param number
     * @return
     */
    public static boolean isEven(int number) {
        return (number & 1) == 0;
    }

}
